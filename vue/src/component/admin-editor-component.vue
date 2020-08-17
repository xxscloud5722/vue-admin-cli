<template>
    <div class="admin-editor">
        <div class="console">
            <a-radio-group v-model="type" class="radio-group">
                <a-radio-button value="1">公众号版本</a-radio-button>
                <a-radio-button value="2">自定义版本</a-radio-button>
            </a-radio-group>
            <input v-if="type === '1'" class="import" @paste="onImport" v-model="importValue"
                   placeholder="Ctr+V粘贴内容，约2分钟"/>
        </div>
        <div class="iframe-edit" v-show="type === '1'">
            <iframe ref="iframe"
                    :src="importEdit"></iframe>
            <div class="spin" v-if="importStatus">
                <a-spin>
                    <a-icon slot="indicator" type="loading" style="font-size: 36px" spin/>
                </a-spin>
            </div>
        </div>
        <quill-editor class="mode-1" :options="options"
                      v-model="inputValue" @change="change"
                      ref="editor" v-if="type === '2'">
        </quill-editor>
        <input type="file" @change="upload" class="editor-file">
    </div>
</template>

<style lang="scss">
    .admin-editor {
        width: 100%;

        & > .console {
            height: 40px;
            line-height: 40px;
            border-top: 1px solid #ddd;
            border-left: 1px solid #ddd;
            border-right: 1px solid #ddd;
            box-sizing: border-box;
            position: relative;
            overflow: hidden;


            & > .radio-group {
                border-top: none !important;
                border-left: none !important;
                border-bottom: none !important;
                border-right: 1px solid #ddd !important;
                height: 40px !important;
                line-height: 40px !important;

                & .ant-radio-button-wrapper {
                    height: 40px !important;
                    line-height: 40px !important;
                    border: none !important;
                }
            }

            & > .import {
                font-size: 13px;
                position: absolute;
                right: 10px;
                width: 170px;
                top: 0;
                bottom: 0;
                display: block;
                margin: 0;
                border: none;
                padding: 0 10px;
                border-left: 1px solid #ddd;
                outline: none;
            }

        }

        & .mode-1 {
            & .ql-toolbar {
                border-color: #dddddd !important;
            }

            & .ql-container {
                border-color: #dddddd !important;
                min-height: 300px;
            }
        }

        & .editor-file {
            opacity: 0;
            display: none;
        }

        & .iframe-edit {
            width: 100% !important;
            min-height: 400px;
            border: 1px solid #ddd;
            padding: 40px 0;
            position: relative;

            & > iframe {
                display: block;
                border: none;
                width: 560px;
                height: 680px;
                margin: auto;
            }

            & > .spin {
                width: 100%;
                height: 100%;
                position: absolute;
                background: #fff;
                left: 0;
                right: 0;
                top: 0;
                bottom: 0;

                & > .ant-spin {
                    display: block;
                    margin: 180px auto;
                }
            }
        }
    }
</style>
<script lang="ts">
    import Vue from 'vue';
    import Component from 'vue-class-component';
    import {Model, Prop, Watch} from "vue-property-decorator";
    import 'quill/dist/quill.core.css'
    import 'quill/dist/quill.snow.css'
    import 'quill/dist/quill.bubble.css'
    import VueQuillEditor from 'vue-quill-editor'

    declare const window: any;
    declare const document: any;
    @Component
    export default class AdminEditorComponent extends Vue {

        @Prop({
            type: String,
            default: ''
        })
        @Model('change')
        public content: string;

        @Prop({
            type: String,
            default: ''
        })
        public height: string;
        public inputValue: string = '';
        public type: string = '1';
        public importStatus: boolean = true
        public importValue: string = '';
        public importEdit: string = '';

        public options: any = {
            placeholder: '',
            modules: {
                toolbar: {
                    container: [
                        ['bold', 'italic', 'underline', 'strike'],        // toggled buttons
                        ['blockquote', 'code-block'],
                        [{'header': 1}, {'header': 2}],               // custom button values
                        [{'list': 'ordered'}, {'list': 'bullet'}],
                        [{'script': 'sub'}, {'script': 'super'}],      // superscript/subscript
                        [{'indent': '-1'}, {'indent': '+1'}],          // outdent/indent
                        [{'direction': 'rtl'}],                         // text direction
                        [{'size': ['small', false, 'large', 'huge']}],  // custom dropdown
                        [{'header': [1, 2, 3, 4, 5, 6, false]}],
                        [{'color': []}, {'background': []}],          // dropdown with defaults from theme
                        [{'font': []}],
                        [{'align': []}],
                        ['link', 'image', 'video'],
                        ['clean']                                         // remove formatting button
                    ],
                    handlers: {
                        'image': function (value) {
                            if (value) {
                                document.querySelector('.editor-file').click()
                            } else {
                                this.quill.format('image', false);
                            }
                        }
                    }
                }
            }
        };

        public created() {
            Vue.use(VueQuillEditor);
            this.importEdit = '//yzj-admin.yuemia.com/edit.html';
        }

        public mounted() {
            this.inputValue = this.content;
            setTimeout(() => {
                this.importStatus = false;
                this.$refs.iframe.contentWindow.postMessage(this.inputValue, '*');
            }, 1500);
        }

        @Watch('inputValue')
        public valueChange() {
        }


        public change() {
            this.$emit('change', this.inputValue);
        }


        public async upload(event: any) {
            const file = event.currentTarget.files[0];
            if (file.size / 1024 / 1024 > 3) {
                return;
            }
            const result = (await this.$api.upload.execute({
                file: file,
                fileName: file.name,
                fileType: file.type.split('/')[1]
            })).asObject();
            if (result.success) {
                console.log(result.data.url);
                const quill = this.$refs.editor.quill;
                const length = quill.getSelection().index;
                quill.insertEmbed(length, 'image', result.data.url);
                quill.setSelection(length + 1);
            }
        }

        public async onImport(event: any) {
            if (this.importStatus) {
                this.$message.info('正在转换，请不要重复复制')
                return
            }
            this.importStatus = true;
            let html = event.clipboardData.getData('text/html');
            if (html == null || html === '') {
                html = event.clipboardData.getData('text/plain');
            }
            const result = (await this.$api.transform.execute({data: html})).asObject();
            this.importStatus = false;
            this.importValue = '';
            if (result.success) {
                this.inputValue = result.data;
                this.$emit('change', this.inputValue);
                this.$refs.iframe.contentWindow.postMessage(this.inputValue, '*');
            } else {
                this.$message.error(result.message)
            }
        }
    }
</script>