<template>
    <div class="system-user-group">
        <admin-area-title-component label="用户组"></admin-area-title-component>
        <admin-table-component class="table" :columns="columns" :url="this.$api.getGroupList" :args="args"
                               :autoLoad="true"
                               :rowKey="row => row.sid"
                               automaticId="sid"
                               ref="table"
                               :isExpandedRowRender="false">
            <div slot="console">
                <div class="row">
                    <div class="item">
                        <a-button type="primary" @click="onLoadData()">刷新数据</a-button>
                    </div>
                    <div class="item">
                        <a-divider type="vertical"/>
                    </div>
                    <div class="item">
                        <a-button type="primary" @click="onGroupDialog(null)">新增用户组</a-button>
                    </div>
                </div>
            </div>
        </admin-table-component>


        <!--  用户组管理 -->
        <admin-pop-component label="管理用户组" v-model="dialog.group">
            <div class="content">
                <div class="row">
                    <div class="label">用户组名称</div>
                    <div class="value">
                        <a-input v-model="groupInfo.name"></a-input>
                    </div>
                </div>
                <div class="row transfer">
                    <div class="label">用户列表</div>
                    <div class="value">
                        <a-transfer
                                :data-source="userList"
                                :target-keys="groupInfo.userList"
                                :titles="['用户列表', '已有用户']"
                                :render="item => item.title"
                                @change="onChange"
                        />
                    </div>
                </div>
                <hr/>
                <a-button type="primary" @click="onSaveGroup">保存数据</a-button>
                <a-popconfirm
                        title="确定删除用户组吗?"
                        ok-text="确定"
                        cancel-text="取消"
                        @confirm="onRemoveGroup"
                        v-if="groupInfo.id !== ''"
                >
                    <a class="remove color-red">删除用户组</a>
                </a-popconfirm>
            </div>
        </admin-pop-component>

    </div>
</template>
<style lang="scss">
    .system-user-group {
        & .content {
            width: 860px;

            & > .row {
                display: flex;
                line-height: 40px;
                height: 40px;
                margin: 10px 0;

                & > .label {
                    width: 80px;
                }
            }

            & > .row.transfer {
                height: 200px;
            }

            & > .remove {
                float: right;
                line-height: 40px
            }

        }
    }
</style>

<script lang="ts">
    import Vue from 'vue';
    import Component from 'vue-class-component';
    import AdminInputComponent from "../component/admin-input-component.vue";
    import AdminDateComponent from "../component/admin-date-component.vue";
    import AdminSelectComponent from "../component/admin-select-component.vue";
    import AdminTableComponent from "../component/admin-table-component.vue";
    import AdminAreaTitleComponent from "../component/admin-area-title-component.vue";
    import AdminEditorComponent from "../component/admin-editor-component.vue";
    import AdminPopComponent from "../component/admin-pop-component.vue";


    @Component({
        components: {
            AdminPopComponent,
            AdminEditorComponent,
            AdminInputComponent,
            AdminDateComponent, AdminSelectComponent, AdminTableComponent, AdminAreaTitleComponent
        }
    })
    export default class SystemUserGroupComponent extends Vue {

        public columns: any = [
            {
                title: '组序号',
                width: 80,
                dataIndex: 'sid',
                key: 'sid',
            },
            {
                title: '组名称',
                width: 200,
                dataIndex: 'name',
                key: 'name',
                customRender: ((text, row) => {
                    return this.$createElement('a', {
                        on: {
                            click: () => {
                                this.onGroupDialog(row);
                            }
                        }
                    }, text)
                })
            },
            {
                title: '创建时间',
                width: '160px',
                dataIndex: 'createTime',
                key: 'createTime',
            },
            {
                title: '',
                key: '_panel',
                dataIndex: '_panel',
            },

        ];
        public args: any = {};
        public dialog: any = {group: false};
        public groupInfo: any = {
            id: '',
            name: '',
            userList: []
        };
        public userList: any[] = [];

        public async mounted() {
            const result = (await this.$api.getUserList.execute({
                args: {},
                currentIndex: 1,
                pageSize: 300
            })).asObject();
            if (result.success) {
                result.data.rows.forEach(it => {
                    this.userList.push({key: it.id, title: it.name, description: ''});
                });
            }
        }

        public async onGroupDialog(item: any) {
            this.dialog.group = true;
            if (item == null) {
                this.groupInfo.id = '';
                this.groupInfo.name = '';
                this.groupInfo.userList = [];
            } else {
                this.groupInfo.id = item.id;
                this.groupInfo.name = item.name;
                this.groupInfo.userList = [];
                const result = (await this.$api.getGroupUserList.execute({id: item.id})).asObject();
                if (result.success) {
                    result.data.forEach(it => {
                        this.groupInfo.userList.push(it.id);
                    });
                }
            }
        }

        public onLoadData() {
            this.$refs.table.loadData();
        }

        public async onSaveGroup() {
            if (this.groupInfo.name === '') {
                return;
            }
            const request: any = {
                id: this.groupInfo.id,
                name: this.groupInfo.name,
                userList: []
            };
            this.groupInfo.userList.forEach(it => {
                request.userList.push({id: it});
            })
            const result = (await this.$api.saveGroup.execute(request)).asObject();
            if (result.success) {
                await this.onLoadData();
                this.dialog.group = false;
                this.$message.success('保存成功')
            } else {
                this.$message.error(result.message)
            }
        }


        public async onRemoveGroup() {
            const result = (await this.$api.removeGroup.execute(this.groupInfo)).asObject();
            if (result.success) {
                await this.onLoadData();
                this.dialog.group = false;
                this.$message.success('保存成功')
            } else {
                this.$message.error(result.message)
            }
        }

        public async onChange(nextTargetKeys: any) {
            this.groupInfo.userList = nextTargetKeys;
        }


    }
</script>