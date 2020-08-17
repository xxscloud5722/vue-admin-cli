<template>
    <div class="admin-date" :class="{'date': type === '0' }">
        <div class="text">{{label}}</div>
        <a-range-picker v-if="defaultValue!=null && defaultValue.length > 0 && type === '1'" class="picker"
                        @change="change"
                      :defaultValue="defaultValue" :allowClear="allowClear"/>
        <a-date-picker v-if="defaultPeriod != null && type === '0'" class="picker"
                       @change="change"
                       :defaultValue="defaultPeriod"
                       :allowClear="allowClear"/>
    </div>
</template>

<style lang="scss">
    .admin-date {
        display: flex;
        line-height: 30px;
        height: 32px;
        background: #fff;
        border: 1px solid #ddd;
        width: 300px;
        box-sizing: border-box;

        & .text {
            padding: 0 8px;
            font-size: 12px;
            background: #efefef;
            text-align: center;
            border-right: 1px solid #ddd;
        }

        & .picker {
            border: none;
            margin: 0;
            font-size: 12px;
            outline: none;
            flex: 1;
            width: 100%;

            & .ant-input {
                border: none;
                height: 30px !important;
                box-shadow: none !important;
            }
        }


    }

    .admin-date.date {
        width: 200px;
    }
</style>
<script lang="ts">
    import Vue from 'vue';
    import Component from 'vue-class-component';
    import {Prop} from 'vue-property-decorator';
    import moment from 'moment';
    import Utils from '../utils/utils';

    const e = new Date(new Date().getTime() + 86400000);
    const s = new Date(new Date().getTime() - 2592000000);
    const ed: Date = new Date();

    @Component
    export default class AdminDateComponent extends Vue {

        @Prop({
            type: String,
            default: '1'
        })
        public type: string;

        @Prop({
            type: Boolean,
            default: true
        })
        public allowClear: boolean;

        @Prop({
            type: String,
            default: ''
        })
        public label: string;

        @Prop({
            type: Date,
            default: () => s
        })
        public defaultStartTime: Date;

        @Prop({
            type: Date,
            default: () => e
        })
        public defaultEndTime: Date;


        public defaultValue: any[] = [];

        public defaultPeriod: any = null;

        public async mounted() {
            await this.init();
        }

        private async init() {
            const startTime = Utils.dateFormat('yyyy/MM/dd', this.defaultStartTime);
            const endTime = Utils.dateFormat('yyyy/MM/dd', this.defaultEndTime);
            const period = Utils.dateFormat('yyyy/MM/dd', ed);
            this.defaultValue = [moment(startTime, 'YYYY/MM/DD'), moment(endTime, 'YYYY/MM/DD')];
            this.defaultPeriod = moment();
            if (this.type === '0') {
                this.$emit('change', [period]);
            } else {
                this.$emit('change', [startTime, endTime]);
            }
        }

        public async change(e: any) {
            let date;
            let date1;
            let date2;
            if (e == null) {
                if (!this.allowClear) {
                    await this.init();
                }
                return;
            }
            switch (this.type) {
                case '0':
                    date = e.toDate();
                    date1 = date.getFullYear() + '-' + (date.getMonth() + 1 > 9 ? date.getMonth() + 1 : '0' + (date.getMonth() + 1)) + '-' + (date.getDate() > 9 ? date.getDate() : '0' + date.getDate()) + ' ' + date.getHours() + ':' + date.getMinutes();
                    this.$emit('change', [date1]);
                    break;
                case '1':
                    if (e.length < 1) {
                        this.$emit('change', []);
                        return;
                    }
                    date = e[0].toDate();
                    date1 = date.getFullYear() + '-' + (date.getMonth() + 1 > 9 ? date.getMonth() + 1 : '0' + (date.getMonth() + 1)) + '-' + (date.getDate() > 9 ? date.getDate() : '0' + date.getDate()) + ' ' + date.getHours() + ':' + date.getMinutes();
                    date = e[1].toDate();
                    date2 = date.getFullYear() + '-' + (date.getMonth() + 1 > 9 ? date.getMonth() + 1 : '0' + (date.getMonth() + 1)) + '-' + (date.getDate() > 9 ? date.getDate() : '0' + date.getDate()) + ' ' + date.getHours() + ':' + date.getMinutes();
                    this.$emit('change', [date1, date2]);
                    break;
            }
        }


    }


</script>