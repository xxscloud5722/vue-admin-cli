<template>
    <div class="admin-table">
        <!-- 头部面板 -->
        <div class="table-console">
            <slot name="console">

            </slot>
        </div>
        <!-- table 表格 -->
        <div class="table">
            <a-table :columns="columns"
                     :dataSource="data"
                     :rowKey="rowKey"
                     @change="handleTableChange"
                     size="middle"
                     ref="table"
                     :loading="loading"
                     :defaultExpandAllRows="true"
                     :pagination="false"
                     :locale="{emptyText:'暂无数据'}"
                     tableLayout="fixed"
                     :class="{'loading': loading || data == null || data.length <= 0}"
                     v-if="!isExpandedRowRender"
            >


            </a-table>

            <a-table :columns="columns"
                     :dataSource="data"
                     :rowKey="rowKey"
                     @change="handleTableChange"
                     size="middle"
                     ref="table"
                     :loading="loading"
                     :defaultExpandAllRows="true"
                     :pagination="false"
                     :locale="{emptyText:'暂无数据'}"
                     tableLayout="fixed"
                     :class="{'loading': loading || data == null || data.length <= 0}"
                     v-if="isExpandedRowRender"
            >

                <div slot="expandedRowRender" slot-scope="row">
                    <slot name="expandedRowRender" :row="row"/>
                </div>
            </a-table>

        </div>

        <!-- 页底面板-->
        <div class="pagination-info" v-if="data != null && data.length > 0">
            <div class="panel">
                <slot name="pagination">

                </slot>
            </div>
            <div class="pagination">
                <div class="page">
                    <select @change="toPage('size')" v-model="pageSize">
                        <option value="20">20</option>
                        <option value="40">40</option>
                        <option value="60">60</option>
                        <option value="80">80</option>
                        <option value="100">100</option>
                    </select>
                </div>
                <div class="page-text">
                    条/页
                </div>
                <div class="panel">
                    <div class="item first" @click="toPage('first')">
                        <i class="iconfont icon-diyiyeshouyeshangyishou"></i>
                    </div>
                    <div class="item previous" @click="toPage('up')">
                        <i class="iconfont icon-shangyiye"></i>
                    </div>
                    <div class="item input">
                        <a-input v-model="currentIndex" @pressEnter="toPage('input')"/>
                    </div>
                    <div class="item text">/{{totalPage}}页</div>
                    <div class="item next" @click="toPage('next')">
                        <i class="iconfont icon-xiayiye"></i>
                    </div>
                    <div class="item last" @click="toPage('last')">
                        <i class="iconfont icon-zuihouyiyemoyexiayishou"></i>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<style lang="scss">


    .admin-table {
        margin: 20px 20px;
        position: relative;


        & .ant-table-wrapper {
            background: #fff;
            -webkit-box-shadow: 0 2px 3px 0 rgba(0, 0, 0, .2);
            box-shadow: 0 2px 3px 0 rgba(0, 0, 0, .2);
            box-sizing: border-box;
        }

        & .table-console {
            & .row {
                height: 32px;
                line-height: 32px;
                margin: 0 0 6px 0;
                display: flex;
                display: -webkit-box;

                & > .item {
                    margin: 0 8px 0 0;
                }
            }
        }

        & .table {

            width: 100%;
            overflow-x: auto;
            -webkit-box-shadow: 0 2px 3px 0 rgba(0, 0, 0, .2);
            box-shadow: 0 2px 3px 0 rgba(0, 0, 0, .2);

            & .ant-table-thead {
                background: #fff !important;

                & th {
                    color: #888;
                    font-weight: 700;
                    line-height: 1.5;
                    padding: 10px 8px !important;
                    border-color: #e5e5e5 !important;

                    & div {
                        font-size: 12px !important;
                    }
                }
            }

            & .ant-table-body {
                margin: 0 !important;
                background: #fff;


                & tr:hover:not(.ant-table-expanded-row) > td {
                    background: #f7f7f7 !important;
                }

                & td {
                    color: #444;
                    font-size: 12px;
                    line-height: 1.5;
                    padding: 10px 8px !important;
                    transition: all 0s, border 0s;
                    border-color: #e5e5e5 !important;
                }
            }

            & .ant-pagination {
                margin: 10px 0;
            }

            & .table-img {
                width: 100%;
                max-width: 50px;
                max-height: 50px;
            }

            & .table-split {
                margin: 0 8px;
                position: relative;
            }

            & .table-split:before {
                content: ' ';
                display: block;
                position: absolute;
                background: #888888;
                width: 1px;
                height: 80%;
                left: 50%;
                top: 10%;
            }

            & .loading {
                height: 90px;
                overflow: hidden
            }

        }

        & .pagination-info {
            background: #fff;
            height: 44px;
            font-size: 12px;
            color: #888;
            display: flex;
            -webkit-box-shadow: 0 2px 3px 0 rgba(0, 0, 0, .2);
            box-shadow: 0 2px 3px 0 rgba(0, 0, 0, .2);

            & > .panel {
                width: 100%;
            }

            & > .pagination {
                display: flex;
                flex: 1;

                & .page {
                    margin: 11px 4px 0 0;

                    & select {
                        outline: none;
                        border: none;
                        background: #fff;
                    }
                }

                & .page-text {
                    line-height: 43px;
                    padding: 0 10px 0 3px;
                    width: 56px;
                }

                & .panel {
                    display: flex;
                    margin: 8px 20px 0 0;


                    & .item {
                        width: 26px;
                        height: 26px;
                        border: 1px solid rgb(221, 221, 221);
                        border-right: none;
                        cursor: pointer;
                        text-align: center;
                    }

                    & .item:hover {
                        background-color: #f2f2f2;
                    }

                    & .first {
                        position: relative;

                        & > i {
                            font-size: 22px;
                            position: absolute;
                            left: 0;
                            top: -4px;
                        }
                    }

                    & .last {
                        border-right: 1px solid rgb(221, 221, 221) !important;
                        position: relative;

                        & > i {
                            font-size: 22px;
                            position: absolute;
                            left: 0;
                            top: -3px;
                        }
                    }

                    & .text {
                        background-color: #f2f2f2;
                        width: 60px;
                        line-height: 24px;
                        padding: 0 5px;
                        font-size: 12px !important;
                    }

                    & .input {
                        width: 40px;

                        & .ant-input {
                            border: none;
                            padding: 0 4px;
                            height: 24px;
                            font-size: 12px;
                            text-align: right;
                        }
                    }
                }
            }
        }
    }
</style>
<script lang="ts">
    import Vue from 'vue';
    import Component from 'vue-class-component';
    import AdminInputComponent from "./admin-input-component.vue";
    import {Prop, Watch} from 'vue-property-decorator';

    declare const window: any;

    @Component({
        components: {AdminInputComponent}
    })
    export default class AdminTableComponent extends Vue {
        public data: any[] = [];
        public pagination: any = {};
        public loading: boolean = false;

        @Prop({
            type: Object,
            default: {}
        })
        public url: any;

        @Prop({
            type: Object,
            default: {}
        })
        public args: any;

        @Prop({
            type: Boolean,
            default: false
        })
        public autoLoad: boolean;

        @Prop({
            type: Array,
            default: []
        })
        public columns!: any[];

        @Prop({
            type: Function,
            default: row => {
                row.id;
            }
        })
        public rowKey: any;

        @Prop({
            type: Boolean,
            default: false
        })
        public isExpandedRowRender: boolean;

        public currentIndex: number = 1;
        public pageSize: number = 20;
        public totalCount: number = 0;
        public totalPage: number = 0;

        @Watch('columns')
        public columnsChange() {
            this.data = [];
            this.currentIndex = 1;
            this.pageSize = 20;
            if (this.autoLoad) {
                this.loadData();
            }
        }


        public handleTableChange() {

        }

        public async mounted() {
            window.addEventListener("resize", this.listener);
            this.listener();
            if (this.autoLoad) {
                this.loadData();
            }
        }

        public async beforeDestroy() {
            window.removeEventListener("resize", this.listener);
        }

        private async listener() {
            let minWidth = 0;
            this.columns.forEach(it => {
                if (it.width == null) {
                    minWidth += 150;
                    return;
                }
                const w = it.width.toString().replace(/px/g, '').trim();
                if (it.width !== '' && !isNaN(Number(w))) {
                    minWidth += Number(w);
                }
            });
            this.$refs.table.$el.setAttribute('style', 'min-width: ' + minWidth + 'px');
        }

        public async loadData() {
            //如果已经没有数据了 初始化一下
            if (this.data == null || this.data.length <= 0) {
                this.currentIndex = 1;
            }
            this.data = [];
            this.loading = true;
            const result: any = (await this.url.execute({
                args: this.args || {},
                currentIndex: this.currentIndex,
                pageSize: this.pageSize
            })).asObject();
            this.loading = false;
            if (!result.success || result.data == null) {
                await this.$message.error(result.message || '系统繁忙，请稍后再试');
                return;
            }
            if (result.data.rows == null) {
                this.data = result.data;
                this.totalCount = result.data.length;
                this.totalPage = 1;
            } else {
                this.data = result.data.rows;
                this.totalCount = result.totalCount;
                if (result.data.totalCount == null || isNaN(Number(result.data.totalCount))) {
                    this.totalPage = 1;
                } else {
                    this.totalPage = result.data.totalCount % this.pageSize === 0 ? result.data.totalCount / this.pageSize : parseInt((result.data.totalCount / this.pageSize).toString()) + 1;
                }
            }
        }

        public async toPage(type: string) {
            switch (type) {
                case 'first':
                    if (this.currentIndex <= 1) {
                        return;
                    }
                    this.currentIndex = 1;
                    break;
                case 'up':
                    if (this.currentIndex - 1 < 1) {
                        return;
                    }
                    this.currentIndex = this.currentIndex - 1;
                    break;
                case 'next':
                    if (this.currentIndex + 1 > this.totalPage) {
                        return;
                    }
                    this.currentIndex = this.currentIndex + 1;
                    break;
                case 'last':
                    if (this.currentIndex >= this.totalPage) {
                        return;
                    }
                    this.currentIndex = this.totalPage;
                    break;
                case 'input':
                    if (isNaN(Number(this.currentIndex))) {
                        this.currentIndex = 1;
                        break;
                    }
                    if (Number(this.currentIndex) < 1) {
                        this.currentIndex = 1;
                        break;
                    }
                    if (Number(this.currentIndex) > this.totalPage) {
                        this.currentIndex = this.totalPage;
                        break;
                    }
                    break;
                case 'size':
                    break;
            }
            this.loadData();
        }


    }


</script>