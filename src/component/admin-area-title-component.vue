<template>
    <div class="admin-area-title">
        <div class="title">
            {{label}}
        </div>
        <div class="console">
            <slot></slot>
        </div>
    </div>
</template>

<style lang="scss">
    .admin-area-title {
        width: 100%;
        height: 50px;
        background: #fff;
        display: flex;
        border-bottom: 1px solid rgb(221, 221, 221);
        padding: 0 20px;

        & > .title {
            line-height: 50px;
            color: #333;
            font-size: 16px;
            font-weight: 700;
            width: 200px;
        }

        & > .console {
            height: 30px;
            flex: 1;
            margin: 10px 0;
        }
    }
</style>
<script lang="ts">
    import Vue from 'vue';
    import Component from 'vue-class-component';
    import {Prop} from 'vue-property-decorator';


    @Component
    export default class AdminAreaTitleComponent extends Vue {
        @Prop({
            type: String,
            default: ''
        })
        public label: string;


    }
</script>