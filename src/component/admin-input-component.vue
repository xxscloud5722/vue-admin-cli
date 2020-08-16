<template>
    <div class="admin-input">
        <div class="text">{{label}}</div>
        <input type="text" v-model="inputValue" @change="change"/>
    </div>
</template>

<style lang="scss">
    .admin-input {
        display: flex;
        line-height: 30px;
        height: 32px;
        background: #fff;
        border: 1px solid #ddd;
        width: 200px;
        box-sizing: border-box;

        & .text {
            padding: 0 8px;
            font-size: 12px;
            background: #efefef;
            text-align: center;
            border-right: 1px solid #ddd;
        }

        & input {
            border: none;
            margin: 0;
            font-size: 12px;
            outline: none;
            padding: 0 6px;
            flex: 1;
            width: 100%;
        }


    }
</style>
<script lang="ts">
    import Vue from 'vue';
    import Component from 'vue-class-component';
    import {Model, Prop} from 'vue-property-decorator';

    @Component
    export default class AdminInputComponent extends Vue {
        @Prop({
            type: String,
            default: ''
        })
        public label: string;

        @Prop({
            type: String,
            default: ''
        })
        @Model('change')
        public value: string = '';

        public inputValue: string = '';

        public mounted() {
            this.inputValue = this.value;
        }

        public change() {
            this.$emit('change', this.inputValue);
        }

    }


</script>