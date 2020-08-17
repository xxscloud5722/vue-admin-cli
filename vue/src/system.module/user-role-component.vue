<template>
    <div class="system-user-role">
        <admin-area-title-component label="用户角色"></admin-area-title-component>
        <admin-table-component class="table" :columns="columns" :url="this.$api.getRoleList" :args="args"
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
                        <a-button type="primary" @click="onProtocolDialog(null)">新增角色</a-button>
                    </div>
                </div>
            </div>
        </admin-table-component>

        <admin-pop-component label="管理角色" v-model="dialog.role">
            <div class="content">
                <div class="row">
                    <div class="label">角色名称</div>
                    <div class="value">
                        <a-input v-model="roleInfo.name"></a-input>
                    </div>
                </div>
                <div class="row transfer">
                    <div class="label">角色权限</div>
                    <div class="value">
                        <a-transfer
                                :data-source="permissionList"
                                :target-keys="roleInfo.permissionList"
                                :titles="['未拥有', '已拥有']"
                                :render="item => item.title"
                                @change="onChange"
                        />
                    </div>
                </div>
                <hr/>
                <a-button type="primary" @click="onSaveRole">保存数据</a-button>

                <a-popconfirm
                        title="确定删除角色吗?"
                        ok-text="确定"
                        cancel-text="取消"
                        @confirm="onRemoveRole"
                >
                    <a class="remove color-red">删除角色</a>
                </a-popconfirm>
            </div>
        </admin-pop-component>
    </div>
</template>
<style lang="scss">
    .system-user-role {
        & .content {
            width: 860px;

            & > .row {
                display: flex;
                line-height: 40px;
                height: 40px;
                margin: 10px 0;

                & > .label {
                    width: 60px;
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
    export default class SystemUserRoleComponent extends Vue {

        public columns: any = [
            {
                title: '角色ID',
                width: 80,
                dataIndex: 'sid',
                key: 'sid',
            },
            {
                title: '角色名称',
                width: 200,
                dataIndex: 'name',
                key: 'name',
                ellipsis: true,
            },
            {
                title: '创建时间',
                width: 160,
                dataIndex: 'createTime',
                key: 'createTime',
            },
            {
                title: '',
                key: '_panel',
                dataIndex: '_panel',
                customRender: ((_, row) => {
                    return this.$createElement('a', {
                        on: {
                            click: () => {
                                this.onProtocolDialog(row);
                            }
                        }
                    }, '编辑角色');
                })
            },

        ];
        public args: any = {};
        public dialog: any = {role: false};
        public roleInfo: any = {
            id: '',
            name: '',
            permissionList: []
        };
        public permissionList: any[] = [];

        public async mounted() {
            const result = (await this.$api.getPermissionList.execute({})).asObject();
            if (result.success) {
                result.data.forEach(it => {
                    this.permissionList.push({key: it.id, title: it.name, description: ''});
                });
            }
        }

        public async onProtocolDialog(item: any) {
            this.dialog.role = true;
            if (item == null) {
                this.roleInfo.id = '';
                this.roleInfo.name = '';
                this.roleInfo.permissionList = [];
            } else {
                this.roleInfo.id = item.id;
                this.roleInfo.name = item.name;
                this.roleInfo.permissionList = [];
                const result = (await this.$api.getRolePermissionList.execute({id: item.id})).asObject();
                if (result.success) {
                    result.data.forEach(it => {
                        this.roleInfo.permissionList.push(it.id);
                    });
                }
            }
        }

        public onLoadData() {
            this.$refs.table.loadData();
        }

        public async onSaveRole() {
            if (this.roleInfo.name === '') {
                return;
            }
            const request: any = {
                id: this.roleInfo.id,
                name: this.roleInfo.name,
                permissionList: []
            };
            this.roleInfo.permissionList.forEach(it => {
                request.permissionList.push({id: it});
            });

            const result = (await this.$api.saveRole.execute(request)).asObject();
            if (result.success) {
                await this.onLoadData();
                this.dialog.role = false;
                this.$message.success('保存成功')
            } else {
                this.$message.error(result.message)
            }
        }

        public async onRemoveRole() {
            const result = (await this.$api.removeRole.execute(this.roleInfo)).asObject();
            if (result.success) {
                await this.onLoadData();
                this.dialog.role = false;
                this.$message.success('保存成功')
            } else {
                this.$message.error(result.message)
            }
        }

        public async onChange(nextTargetKeys: any) {
            this.roleInfo.permissionList = nextTargetKeys;
        }
    }
</script>