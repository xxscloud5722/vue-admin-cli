<template>
    <div class="admin-upload" :class="type">
        <div class="image" v-for="item of images">
            <img :src="item.url"/>
            <div class="close" @click="onRemover(item)">
                <i class="iconfont icon-guanbi"></i>
            </div>
            <div class="preview" @click="onPreview(item)">
                <div class="span">预览</div>
            </div>
        </div>
        <div class="upload" v-if="images.length !== totalPage">
            <div class="icon">
                <svg viewBox="64 64 896 896" data-icon="plus" width="1em" height="1em" fill="currentColor"
                     aria-hidden="true" focusable="false" class="">
                    <path d="M482 152h60q8 0 8 8v704q0 8-8 8h-60q-8 0-8-8V160q0-8 8-8z"></path>
                    <path d="M176 474h672q8 0 8 8v60q0 8-8 8H176q-8 0-8-8v-60q0-8 8-8z"></path>
                </svg>
            </div>
            <div class="text" v-if="type !== 'single'">上传图片 ({{images.length}}/{{totalPage}})</div>
            <input type="file" @change="upload">
        </div>
    </div>
</template>

<style lang="scss">
    .admin-upload {
        height: 120px;

        & .image {
            width: 100px;
            height: 100px;
            border: 1px solid #d9d9d9;
            background: #fff;
            float: left;
            margin: 0 20px 20px 0;
            cursor: pointer;
            position: relative;

            & > img {
                width: 98px;
                height: 98px;
            }

            & > .close {
                position: absolute;
                right: -5px;
                top: -5px;
                width: 18px;
                height: 18px;
                background: rgba(0, 0, 0, 0.8);
                border-radius: 50%;
                z-index: 99;
                border: 1px solid #fff;
                display: none;


                & > i {
                    position: absolute;
                    font-size: 12px !important;
                    left: 2px;
                    top: -11px;
                    color: #fff;
                    transform: scale(0.8, 0.8);
                }
            }

            & > .preview {
                display: none;
                position: absolute;
                bottom: 0;
                left: 0;
                right: 0;
                height: 24px;
                line-height: 24px;
                background: rgba(0, 0, 0, 0.6);
                text-align: center;
                color: #fff;
                font-size: 12px;
            }
        }

        & .image:hover {
            & > .close {
                display: block;
            }

            & > .preview {
                display: block;
            }
        }

        & .upload {
            width: 100px;
            height: 100px;
            background: #fafafa;
            position: relative;
            cursor: pointer;
            border: 1px dashed #d9d9d9;
            float: left;
            margin: 0 20px 20px 0;
            overflow: hidden;

            & > .icon {
                font-size: 32px;
                color: #999;
                width: 32px;
                position: absolute;
                top: 20px;
                left: 35px;
            }

            & > .text {
                text-align: center;
                position: absolute;
                bottom: 1px;
                left: 0;
                right: 0;
                color: #666;
            }

            & > input {
                position: absolute;
                left: 0;
                opacity: 0;
                right: 0;
                top: 0;
                bottom: 0;
                cursor: pointer;
            }
        }
    }

    .admin-upload.single {
        height: 56px;
        width: 56px;
        margin: 10px auto;

        & .upload {
            width: 56px;
            height: 56px;
            margin: 0;

            & > .icon {
                font-size: 18px;
                color: #999;
                width: 18px;
                height: 18px;
                position: absolute;
                top: -12px;
                left: 19px;
            }
        }

        & .image {
            width: 56px;
            height: 56px;
            margin: 0;

            & > img {
                width: 54px;
                height: 54px;
                position: absolute;
            }

            & > .close {
                right: -7px;
                top: -7px;


                & > i {
                    left: 2px;
                    top: -34px;
                }
            }

            & > .preview {
                height: 12px;
                line-height: 12px;

                & > .span {
                    font-size: 12px;
                    transform: scale(0.7, 0.7);
                }
            }
        }
    }
</style>
<script lang="ts">
    import Vue from 'vue';
    import Component from 'vue-class-component';
    import AdminInputComponent from "./admin-input-component.vue";
    import {Model, Prop} from 'vue-property-decorator';

    @Component({
        components: {AdminInputComponent}
    })
    export default class AdminUploadComponent extends Vue {
        @Prop({
            type: Array,
            default: []
        })
        @Model('change')
        public images: any[];

        @Prop({
            type: Number,
            default: 1
        })
        public totalPage: number;

        @Prop({
            type: String,
            default: ''
        })
        public type: string;


        public async upload(event: any) {
            const file = event.currentTarget.files[0];
            if (file.size / 1024 / 1024 > 3) {
                return;
            }

            const result = (await this['$api'].upload.execute({
                file: file,
                fileName: file.name,
                fileType: file.type.split('/')[1]
            })).asObject();
            if (result.success) {
                this.images.push(result.data);
            }
            this.$emit('change', this.images);
        }

        public onRemover(item: any) {
            for (let i = 0; i < this.images.length; i++) {
                if (this.images[i] === item) {
                    this.images.splice(i, 1);
                }
            }
        }

        public onPreview(item: any) {
            console.log(item);
        }
    }


</script>