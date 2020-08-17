<template>
    <div class="admin-upload" :class="type">
        <div class="image" v-for="item of images" :style="{width:width+'px',height:height+'px'}">
            <img :src="item == null || item.url == null ? item : item.url"/>
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


        <a-modal title="编辑图片" v-model="dialog.edit" width="1000px" height="680px" @ok="onSaveImage"
                 :maskClosable="false">
            <div class="admin-upload-cropper-content">
                <vueCropper
                        ref="cropper"
                        :img="option.img"
                        :outputSize="option.size"
                        :outputType="option.outputType"
                        :info="option.info"
                        :full="option.full"
                        :canMove="option.canMove"
                        :canMoveBox="option.canMoveBox"
                        :original="option.original"
                        :autoCrop="option.autoCrop"
                        :autoCropWidth="option.autoCropWidth"
                        :autoCropHeight="option.autoCropHeight"
                        :fixedNumber="option.fixedNumber"
                        :fixedBox="option.fixedBox"
                ></vueCropper>
            </div>
            <div class="admin-upload-cropper-panel">
                <a class="link" @click="onChangeScale(1)">放大</a>
                &nbsp;&nbsp;
                <a class="link" @click="onChangeScale(-1)">缩小</a>
                &nbsp;&nbsp;
                <a class="link" @click="onRotateLeft()">左旋转</a>
                &nbsp;&nbsp;
                <a class="link" @click="onRotateRight()">右旋转</a>
            </div>
        </a-modal>
    </div>
</template>

<style lang="scss">
    .admin-upload {
        height: 120px;

        & .image {
            width: 100%;
            height: 100%;
            border: 1px solid #d9d9d9;
            background: #fff;
            float: left;
            margin: 0 20px 20px 0;
            cursor: pointer;
            position: relative;


            & > img {
                width: 100%;
                height: 100%;
                display: block;
                margin: auto;
                object-fit: cover;
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
                    top: -7px;
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

    .admin-upload-cropper-content {
        height: 640px;
        position: relative;
    }

    .admin-upload-cropper-panel {
        height: 40px;
        line-height: 40px;

        & .link {
            font-size: 14px;
            line-height: 45px;
            height: 45px;
            user-select: none;
            -webkit-user-select: none;
        }
    }
</style>
<script lang="ts">
    import Vue from 'vue';
    import Component from 'vue-class-component';
    import AdminInputComponent from "./admin-input-component.vue";
    import {Model, Prop} from 'vue-property-decorator';
    import VueCropper from 'vue-cropper';

    declare const window: any;
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

        @Prop({
            type: Number,
            default: 100
        })
        public width: number;

        @Prop({
            type: Number,
            default: 100
        })
        public height: number;


        @Prop({
            type: Boolean,
            default: false
        })
        public edit: boolean;

        @Prop({
            type: Number,
            default: 150
        })
        public cropWidth: boolean;

        @Prop({
            type: Number,
            default: 150
        })
        public cropHeight: boolean;

        @Prop({
            type: Boolean,
            default: false
        })
        public cropFixed: boolean;

        public option: any = {
            img: '', // 裁剪图片的地址
            fileName: '',
            info: true, // 裁剪框的大小信息
            outputSize: 1, // 剪切后的图片质量（0.1-1）
            full: true, // 输出原图比例截图 props名full
            outputType: 'png', // 裁剪生成额图片的格式
            canMove: true,  // 能否拖动图片
            original: false,  // 上传图片是否显示原始宽高
            canMoveBox: true,  // 能否拖动截图框
            autoCrop: true, // 是否默认生成截图框
            autoCropWidth: this.cropWidth,
            autoCropHeight: this.cropHeight,
            fixedBox: this.cropFixed, // 截图框固定大小,
            fixedNumber: null//截图宽高比例
        };

        public dialog = {edit: false};

        public created() {
            Vue.use(VueCropper);
        }


        public async upload(event: any) {
            const file = event.currentTarget.files[0];
            if (file.size / 1024 / 1024 > 3) {
                return;
            }
            if (this.edit) {
                const reader = new window.FileReader();
                reader.onload = (e) => {
                    let data;
                    if (typeof e.target.result === 'object') {
                        // 把Array Buffer转化为blob 如果是base64不需要
                        data = window.URL.createObjectURL(new window.Blob([e.target.result]))
                    } else {
                        data = e.target.result
                    }
                    this.option.fileName = file.name;
                    this.option.img = data;
                }
                reader.readAsArrayBuffer(file);
                this.dialog.edit = true;
            } else {
                await this.onSaveImage(event);
            }
        }

        public onRemover(item: any) {
            for (let i = 0; i < this.images.length; i++) {
                if (this.images[i] === item) {
                    this.images.splice(i, 1);
                }
            }
        }

        public onPreview(item: any) {
            window.open(item.url == null ? item : item.url);
        }

        public onChangeScale(value: number) {
            this.$refs.cropper.changeScale(value);
        }

        public onRotateLeft() {
            this.$refs.cropper.rotateLeft();
        }

        public onRotateRight() {
            this.$refs.cropper.rotateRight();
        }

        public async onSaveImage(event: any) {
            if (event.currentTarget != null && event.currentTarget.files) {
                const file = event.currentTarget.files[0];
                const result = (await this.$api.upload.execute({
                    file: file,
                    fileName: file.name,
                    fileType: file.type.split('/')[1]
                })).asObject();
                if (result.success) {
                    this.images.push(result.data);
                }
                this.$emit('change', this.images);
            } else {
                this.dialog.edit = false;
                this.$refs.cropper.getCropData(async (data) => {
                    const mine = this.option.fileName != null && this.option.fileName.split('/').length > 0 ? this.option.fileName.split('/')[1] : 'wz';
                    const array = data.split(',');
                    const bytes = window.atob(array[1]);
                    let n = bytes.length;
                    const ia = new Uint8Array(n);
                    while (n--) {
                        ia[n] = bytes.charCodeAt(n);
                    }
                    const file = new window.File([ia], this.option.fileName, {type: mine});
                    const result = (await this.$api.upload.execute({
                        file: file,
                        fileName: this.option.fileName,
                        fileType: mine
                    })).asObject();
                    if (result.success) {
                        this.images.push(result.data);
                    }
                    this.$emit('change', this.images);
                });
            }
        }
    }


</script>