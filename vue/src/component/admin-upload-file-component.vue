<template>
    <div class="admin-upload-file">
        <a @click="onAddFile" v-if="files != null && files.length < total">添加文件</a>
        <div class="list">
            <div class="row" v-for="item of files">
                <div class="icon">
                    <i class="iconfont icon-huixingzhen" v-if="item.success"></i>
                    <a-icon type="loading" v-else style="font-size: 14px; margin-top: 5px"/>
                </div>
                &nbsp;&nbsp;
                <div class="name">{{item.fileName}}</div>
                <div class="close" @click="onRemover(item)">
                    <i class="iconfont icon-guanbi"></i>
                </div>
            </div>
        </div>
        <input type="file" @change="upload" class="admin-upload-file-input" hidden>
    </div>
</template>

<style lang="scss">
    .admin-upload-file {
        max-width: 400px;
        min-width: 200px;

        & > a {
            font-size: 13px;
        }

        & > .list {
            & > .row {
                height: 32px;
                line-height: 32px;
                cursor: pointer;
                display: flex;
                padding: 0 6px;

                & > .name {
                    color: #333333;
                    width: 100%;
                }

                & > .icon {
                    flex: 1;
                }

                & > .close {
                    flex: 1;
                    min-width: 24px;
                    height: 30px;
                    text-align: center;
                    margin-top: 1px;

                    & > i {
                        font-size: 13px;
                        color: #666;
                    }

                }

                & > .close:hover {
                    & > i {
                        color: #f5222d !important;
                    }
                }
            }

            & > .row:hover {
                background: #eee;
            }
        }
    }
</style>
<script lang="ts">
    import Vue from 'vue';
    import Component from 'vue-class-component';
    import AdminInputComponent from "./admin-input-component.vue";
    import {Model, Prop} from 'vue-property-decorator';

    declare const window: any;
    declare const document: any;
    @Component({
        components: {AdminInputComponent}
    })
    export default class AdminUploadComponent extends Vue {
        @Prop({
            type: Array,
            default: () => []
        })
        @Model('change')
        public files: any[];

        @Prop({
            type: Number,
            default: 1
        })
        public total: number;

        public async onAddFile() {
            setTimeout(() => {
                document.querySelector('.admin-upload-file-input').click();
            }, 0);
        }

        public async upload(event: any) {
            const file = event.currentTarget.files[0];
            if (file.size / 1024 / 1024 > 100) {
                return;
            }
            const item: any = {
                fileName: file.name,
                success: false,
                url: ''
            };
            this.files.push(item);

            const result = (await this.$api.upload.execute({
                file: file,
                fileName: file.name,
                fileType: file.type.split('/')[1]
            })).asObject();
            if (result.success) {
                item.resourcesId = result.data.resourcesId;
                item.url = result.data.url;
                item.success = true;
            }
            this.$emit('change', this.files);
        }

        public onRemover(item: any) {
            for (let i = 0; i < this.files.length; i++) {
                if (this.files[i] === item) {
                    this.files.splice(i, 1);
                }
            }
        }


    }


</script>