<template>
    <div class="system-user">
        <admin-area-title-component label="用户列表"></admin-area-title-component>
        <admin-table-component class="table" :columns="columns" :url="this.$api.getUserList" :args="args"
                               :autoLoad="true"
                               :rowKey="row => row.sid"
                               automaticId="sid"
                               ref="table"
                               :isExpandedRowRender="false">
            <div slot="console">
                <div class="row">
                    <div class="item">
                        <admin-input-component label="姓名" v-model="args.name"/>
                    </div>
                    <div class="item">
                        <a-button type="primary" @click="onLoadData()">搜索数据</a-button>
                    </div>
                    <div class="item">
                        <a-divider type="vertical"/>
                    </div>
                    <div class="item">
                        <a-button type="primary" @click="onUserDialog(null)">添加用户</a-button>
                    </div>
                </div>
            </div>
        </admin-table-component>

        <admin-pop-component label="用户管理" v-model="dialog.user">
            <div class="content">
                <div class="row">
                    <div class="label">用户姓名</div>
                    <div class="value">
                        <a-input v-model="userInfo.name"></a-input>
                    </div>
                </div>
                <div class="row">
                    <div class="label">用户手机号</div>
                    <div class="value">
                        <a-input v-model="userInfo.phone"></a-input>
                    </div>
                </div>
                <div class="row">
                    <div class="label">用户邮箱</div>
                    <div class="value">
                        <a-input v-model="userInfo.email"></a-input>
                    </div>
                </div>
                <div class="row">
                    <div class="label">用户账号</div>
                    <div class="value">
                        <a-input v-model="userInfo.account"></a-input>
                    </div>
                </div>
                <div class="row">
                    <div class="label">用户密码</div>
                    <div class="value">
                        <a-input type="password" :disabled="userInfo.id !== ''" v-model="userInfo.password"></a-input>
                    </div>
                </div>
                <div class="row">
                    <div class="label">角色</div>
                    <div class="value">
                        <a-select v-model="userInfo.roleId">
                            <a-select-option v-for="i of roleList" :value="i.id">{{i.name}}</a-select-option>
                        </a-select>
                    </div>
                </div>
                <div class="row">
                    <div class="label">用户组</div>
                    <div class="value">
                        <a-select v-model="userInfo.groupId">
                            <a-select-option v-for="i of groupList" :value="i.id">
                                {{i.name}}
                            </a-select-option>
                        </a-select>
                    </div>
                </div>
                <hr/>
                <a-button type="primary" @click="onSaveUser">保存数据</a-button>
                <a-popconfirm
                        title="确定删除用户吗?"
                        ok-text="确定"
                        cancel-text="取消"
                        @confirm="onRemoveUser"
                        v-if="userInfo.id !== ''"
                >
                    <a class="remove color-red">删除用户</a>
                </a-popconfirm>
            </div>
        </admin-pop-component>
    </div>
</template>
<style lang="scss">
    .system-user {
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

                & > .value {
                    & .ant-select {
                        width: 170px;
                    }
                }
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
    export default class SystemUserComponent extends Vue {

        public columns: any = [
            {
                title: '用户序号',
                width: 80,
                dataIndex: 'sid',
                key: 'sid',
            },
            {
                title: '用户姓名',
                width: 140,
                dataIndex: 'name',
                key: 'name',
                ellipsis: true,
            },
            {
                title: '用户账号',
                width: 160,
                dataIndex: 'account',
                key: 'account',
                customRender: ((text, row) => {
                    return this.$createElement('a', {
                        attrs: {},
                        on: {
                            click: async () => {
                                await this.onUserDialog(row);
                            }
                        }
                    }, text);
                })
            },
            {
                title: '用户手机号',
                width: 160,
                dataIndex: 'phone',
                key: 'phone',
            },
            {
                title: '用户邮箱',
                width: 200,
                dataIndex: 'email',
                key: 'email',
            },
            {
                title: '用户状态',
                width: 80,
                dataIndex: 'status',
                key: 'status',
                customRender: ((text) => {
                    return this.$createElement('span', {
                        attrs: {
                            class: text === 1 ? 'color-blue' : 'color-red'
                        }
                    }, text === 1 ? '在职 (正常)' : '离职 (冻结)');
                })
            },
            {
                title: '用户角色',
                width: 120,
                dataIndex: 'role',
                key: 'role',
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
                        attrs: {},
                        on: {
                            click: () => {
                                this.onSetUserStatus(row);
                            }
                        }
                    }, '冻结用户');
                })
            },

        ];
        public args: any = {};
        public dialog: any = {user: false};
        public userInfo: any = {
            id: '',
            name: '',
            phone: '',
            email: '',
            account: '',
            password: '',
            roleId: '',
            groupId: ''
        };
        public roleList: any[] | null = null;
        public groupList: any[] | null = null;

        public async mounted() {
            const roleResult = (await this.$api.getRoleList.execute()).asObject();
            if (roleResult.success) {
                roleResult.data.forEach(it => it.id = it.id.toString());
                this.roleList = roleResult.data;
            }

            const groupResult = (await this.$api.getGroupList.execute()).asObject();
            if (groupResult.success) {
                groupResult.data.forEach(it => it.id = it.id.toString());
                this.groupList = groupResult.data;
            }
        }

        public async onUserDialog(item: any) {
            this.dialog.user = true;
            if (item == null) {
                this.userInfo = {
                    id: '',
                    name: '',
                    phone: '',
                    email: '',
                    account: '',
                    password: '',
                    roleId: '',
                    groupId: ''
                };
            } else {
                this.userInfo = {
                    id: item.id,
                    name: item.name,
                    phone: item.phone,
                    account: item.account,
                    email: item.email,
                    password: '***************',
                    roleId: item.roleId,
                    groupId: item.groupId
                };
            }
        }

        public onLoadData() {
            this.$refs.table.loadData();
        }

        public async onSaveUser() {
            if (this.userInfo.name === '') {
                return;
            }
            if (this.userInfo.phone === '') {
                return;
            }
            if (this.userInfo.email === '') {
                return;
            }
            if (this.userInfo.account === '') {
                return;
            }
            if (this.userInfo.roleId === '') {
                return;
            }
            if (this.userInfo.groupId === '') {
                return;
            }
            const result = (await this.$api.saveUser.execute(this.userInfo)).asObject();
            if (result.success) {
                await this.onLoadData();
                this.dialog.user = false;
                this.$message.success('保存成功')
            } else {
                this.$message.error(result.message)
            }
        }

        public async onSetUserStatus(item: any) {
            const result = (await this.$api.setUserStatus.execute({
                id: item.id,
                status: item.status === 0 ? 1 : 0
            })).asObject();
            if (result.success) {
                await this.onLoadData();
                this.$message.success('保存成功');
            } else {
                this.$message.error(result.message);
            }
        }

        public async onRemoveUser() {
            const result = (await this.$api.removeUser.execute({
                id: this.userInfo.id
            })).asObject();
            if (result.success) {
                await this.onLoadData();
                this.dialog.user = false;
                this.$message.success('保存成功');
            } else {
                this.$message.error(result.message);
            }
        }
    }
</script>