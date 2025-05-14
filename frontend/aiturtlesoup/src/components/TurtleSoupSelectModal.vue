<template>
  <a-modal
    :open="visible"
    title="选择海龟汤"
    width="80%"
    :footer="null"
    class="turtle-soup-modal"
    @cancel="handleCancel"
  >
    <div class="filter-container">
      <a-form layout="inline" :model="queryParams">
        <a-form-item label="标题">
          <a-input
            v-model:value="queryParams.title"
            placeholder="输入标题关键词"
            allow-clear
            @pressEnter="handleSearch"
          />
        </a-form-item>

        <a-form-item label="谜面">
          <a-input
            v-model:value="queryParams.riddle"
            placeholder="输入谜面关键词"
            allow-clear
            @pressEnter="handleSearch"
          />
        </a-form-item>

        <a-form-item label="难度">
          <a-select
            v-model:value="queryParams.difficulty"
            placeholder="选择难度"
            style="width: 120px"
            allow-clear
          >
            <a-select-option :value="1">简单</a-select-option>
            <a-select-option :value="2">中等</a-select-option>
            <a-select-option :value="3">困难</a-select-option>
          </a-select>
        </a-form-item>

        <a-form-item>
          <a-button type="primary" @click="handleSearch">
            <template #icon><SearchOutlined /></template>
            搜索
          </a-button>
          <a-button style="margin-left: 8px" @click="resetSearch">
            <template #icon><RedoOutlined /></template>
            重置
          </a-button>
        </a-form-item>
      </a-form>
    </div>

    <a-divider />

    <div class="list-container">
      <a-table
        :dataSource="dataSource"
        :columns="columns"
        :pagination="false"
        :loading="loading"
        rowKey="id"
        @rowClick="handleRowClick"
      >
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'difficulty'">
            <a-tag :color="getDifficultyColor(record.difficulty)">
              {{ formatDifficulty(record.difficulty) }}
            </a-tag>
          </template>
          <template v-else-if="column.key === 'actions'">
            <a-button type="link" @click="handleSelect(record)">选择</a-button>
          </template>
        </template>
      </a-table>

      <div class="pagination-container">
        <a-pagination
          v-model:current="queryParams.current"
          :total="total"
          :pageSize="queryParams.pageSize"
          show-less-items
          show-size-changer
          :pageSizeOptions="['5', '10', '20', '50']"
          @change="handlePageChange"
          @showSizeChange="handleSizeChange"
        />
      </div>
    </div>
  </a-modal>
</template>

<script lang="ts" setup>
import { ref, watch, onMounted } from 'vue'
import { SearchOutlined, RedoOutlined } from '@ant-design/icons-vue'
import type { TurtleSoup, TurtleSoupQueryRequest } from '../backend'
import { TurtleSoupControllerService } from '../backend'

interface Props {
  visible: boolean
}

interface Emits {
  (e: 'onCancel', value: boolean): void
  (e: 'onSelectSoup', id: TurtleSoup): void
}

const props = defineProps<Props>()
const emit = defineEmits<Emits>()

// 数据状态
const loading = ref(false)
const dataSource = ref<TurtleSoup[]>([])
const total = ref(0)

// 查询参数
const queryParams = ref({
  current: 1,
  pageSize: 10,
  title: '',
  riddle: '',
  difficulty: undefined,
  sortField: '',
  sortOrder: ''
})

// 表格列配置
const columns = ref([
  {
    title: '标题',
    dataIndex: 'title',
    key: 'title',
    width: '25%'
  },
  {
    title: '谜面',
    dataIndex: 'riddle',
    key: 'riddle',
    width: '35%',
    ellipsis: true
  },
  {
    title: '难度',
    dataIndex: 'difficulty',
    key: 'difficulty',
    width: '15%'
  },
  {
    title: '创建时间',
    dataIndex: 'createTime',
    key: 'createTime',
    width: '15%'
  },
  {
    title: '操作',
    key: 'actions',
    width: '10%'
  }
])

// 获取海龟汤列表
const fetchData = async () => {
  loading.value = true
  try {
    const res = await TurtleSoupControllerService.listTurtleSoupByPagesUsingPost(
      {
        current: String(queryParams.value.current),
        pageSize: String(queryParams.value.pageSize),
        title: queryParams.value.title,
        riddle: queryParams.value.riddle,
        difficulty: queryParams.value.difficulty,
        sortField: queryParams.value.sortField,
        sortOrder: queryParams.value.sortOrder
      }
    )
    if (res.code === 0 && res.data) {
      dataSource.value = res.data.records || []
      total.value = Number(res.data.total) || 0
    }
  } catch (error) {
    console.error('获取海龟汤列表失败:', error)
  } finally {
    loading.value = false
  }
}

// 搜索
const handleSearch = () => {
  queryParams.value.current = 1
  fetchData()
}

// 重置搜索
const resetSearch = () => {
  queryParams.value = {
    current: 1,
    pageSize: 10,
    title: '',
    riddle: '',
    difficulty: undefined,
    sortField: '',
    sortOrder: ''
  }
  fetchData()
}

// 分页变化
const handlePageChange = (page: number) => {
  queryParams.value.current = page
  fetchData()
}

// 每页条数变化
const handleSizeChange = (current: number, size: number) => {
  queryParams.value.pageSize = size
  queryParams.value.current = 1
  fetchData()
}

// 行点击
const handleRowClick = (record: TurtleSoup) => {
  handleSelect(record)
}

// 选择海龟汤
const handleSelect = (record: TurtleSoup) => {
  emit('onSelectSoup', record)
  emit('onCancel', false)
}

// 取消
const handleCancel = () => {

  console.log('取消')
  emit('onCancel', false)
}

// 难度格式化
const formatDifficulty = (difficulty?: number) => {
  if (!difficulty) return '未知'
  const map = { 1: '简单', 2: '中等', 3: '困难' }
  return map[difficulty as keyof typeof map] || '未知'
}

// 难度颜色
const getDifficultyColor = (difficulty?: number) => {
  if (!difficulty) return 'default'
  const map = { 1: 'green', 2: 'orange', 3: 'red' }
  return map[difficulty as keyof typeof map] || 'default'
}

// 监听visible变化
watch(() => props.visible, (val) => {
  if (val) {
    fetchData()
  }
})

onMounted(() => {
  if (props.visible) {
    fetchData()
  }
})
</script>

<style scoped>
.turtle-soup-modal {
  top: 20px;
}

.turtle-soup-modal :deep(.ant-modal-title) {
  color: #00ff88;
  font-weight: bold;
}

.turtle-soup-modal :deep(.ant-modal-content) {
  background-color: #121212;
  border: 1px solid #00ff88;
}

.turtle-soup-modal :deep(.ant-modal-header) {
  background-color: #121212;
  border-bottom: 1px solid #333;
}

.filter-container {
  margin-bottom: 16px;
}

.list-container {
  min-height: 400px;
}

.pagination-container {
  margin-top: 16px;
  text-align: right;
}

/* 表格样式 */
.turtle-soup-modal :deep(.ant-table) {
  background-color: #1e1e1e;
  color: #e0e0e0;
}

.turtle-soup-modal :deep(.ant-table-thead > tr > th) {
  background-color: #0a1a0a;
  color: #00ff88;
  border-bottom: 1px solid #333;
}

.turtle-soup-modal :deep(.ant-table-tbody > tr > td) {
  border-bottom: 1px solid #333;
  color: #e0ffe0;
}

.turtle-soup-modal :deep(.ant-table-tbody > tr:hover > td) {
  background-color: #0a2a0a !important;
  cursor: pointer;
}

/* 输入框样式 */
.turtle-soup-modal :deep(.ant-input),
.turtle-soup-modal :deep(.ant-select-selector) {
  background-color: #1e1e1e;
  border-color: #333;
  color: #e0ffe0;
}

.turtle-soup-modal :deep(.ant-input):hover,
.turtle-soup-modal :deep(.ant-select-selector):hover {
  border-color: #00ff88;
}

.turtle-soup-modal :deep(.ant-input-affix-wrapper) {
  background-color: #1e1e1e;
  border-color: #333;
}

/* 按钮样式 */
.turtle-soup-modal :deep(.ant-btn-primary) {
  background-color: #00aa55;
  border-color: #00aa55;
}

.turtle-soup-modal :deep(.ant-btn-primary:hover) {
  background-color: #00cc77;
  border-color: #00cc77;
}

/* 分页样式 */
.turtle-soup-modal :deep(.ant-pagination-item a) {
  color: #e0ffe0;
}

.turtle-soup-modal :deep(.ant-pagination-item-active) {
  border-color: #00ff88;
}

.turtle-soup-modal :deep(.ant-pagination-item-active a) {
  color: #00ff88;
}

.turtle-soup-modal :deep(.ant-pagination-options-quick-jumper input) {
  background-color: #1e1e1e;
  border-color: #333;
  color: #e0ffe0;
}
</style>
