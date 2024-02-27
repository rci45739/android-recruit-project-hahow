**毫無技巧，全是將需求與畫面完整呈現的感情**
**(2/28留:想想還是有太多地方可以revampQQ,新開一個branch會持續發了pr可以無視，因為已超過截止日期所以特別開其他branch來做revamp)**
**(2/28留:新增hilt將原本viewModel repository解耦合，新pr不會做Merge直到審核結束，單純自己修修改改玩玩)**
## BASE
**BaseFragment** and **BaseActivity**。
- 基礎的Fragment類與Activity類，用於绑定 ViewModel 和 ViewDataBinding，並提供了一些lifecycle方法和抽象方法供子類實現。

- ViewModel綁定:onCreateView 方法中，通過 ViewModelProvider 獲取傳入的 ViewModel 對象，並其其赋值給 viewModel。

- DataBinding绑定：onCreateView 方法中，使用 DataBindingUtil.inflate 方法將layout 與 ViewDataBinding 類型绑定，並將其赋值给dataBinding属性。

- lifecycle方法：onViewCreated 方法中，調用一系列方法来init data、layout和 ViewModel、設置 AppBar。

- 抽象方法：定義了一系列抽象方法，用于在子類中實現特定的logic，例如獲取布局 ID、處理 Bundle、綁定布局和 ViewModel、init data、init view 和 setting Observer。

## 設計模式
- **MVVM**：`將專案拆分為Model,View,ViewModel與Repository`
- **Model**:`代表了數據業務邏輯`
- **ViewModel**: `是連接 View 和 Model 的中介層`
- **Repository**: `用于管理应用程序的数据源 , 它封装了data的獲取和儲存邏輯，這次專案是在本地數據庫獲取data，也提供了ViewModel可以調用的方法来獲取data。`

## Library or Third party Library
- **Glide**: `用於加載、緩存和顯示圖片`
- **Room**: `是一個在 SQLite database上提供抽像層的持久性庫，我將它用來當Data Cache`
- **Gson**: `用來將JSON字符串反序列化`
- **Coroutines**: `協程用來做一些耗時操作`
- **SwipeRefreshLayout**: `用來做下拉刷新的layout`
- **DataBinding**: `綁定View與數據模型`

## Library or Third party Library
- **Glide**: `用於加載、緩存和顯示圖片`
- **Room**: `是一個在 SQLite database上提供抽像層的持久性庫，我將它用來當Data Cache`
- **Gson**: `用來將JSON字符串反序列化`
- **Coroutines**: `協程用來做一些耗時操作`
- **SwipeRefreshLayout**: `用來做下拉刷新的layout`
- **DataBinding**: `綁定View與數據模型`
- **Recyclerview + DiffUtil**:
  `RecyclerView用於顯示數據列表，DiffUtil用于計算list之間的差異並更新提高性能`

## process
- init data時呼叫ViewModel內fetch method
- 如果cache有資料且並非下拉刷新，直接將local database的資料取出顯示
- 如果cache沒有資料且並非下拉刷新就先讀取提供的Data.json，並同時將解析玩的資料存進local database當cache
- 下拉刷新時直接清存cache資料，並重新走讀取提供的Data.json這個flow並將其set進local database

**深色模式也有考慮進去，點擊列表Item時有Ripple anim**

## Utils
- 切換fragment
- 課程label status
- 課程progress bar
- 將這三項抽離出來可以供其他地方使用

#### UI 設計
<img width="300" alt="CleanShot 2021-12-09 at 10 59 30@2x" src="https://user-images.githubusercontent.com/76472179/145350022-b4624fe0-2612-4fdb-950c-da6898ca4166.png">

