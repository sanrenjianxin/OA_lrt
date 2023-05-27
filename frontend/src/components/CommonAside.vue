<template>
    <el-menu 
    class="el-menu-vertical-demo" 
    text-color="#fff" 
    background-color="#545c64" 
    :collapse="$store.state.isCollapse"
    :collapse-transition="false" 
    >
        <h3 v-show="$store.state.isCollapse">后台</h3>
        <h3 v-show="!$store.state.isCollapse">后台管理</h3>
        <el-menu-item 
        :index="item.path" 
        v-for="item in noChildren()" 
        :key="item.path"
        @click="clickMenu(item)"
        >
            <!-- vue3动态引入icon -->
            <el-icon>
                <component :is="item.icon"></component>
            </el-icon>
            <span>{{item.label }}</span>
        </el-menu-item>
        <el-sub-menu 
        :index="item.label" 
        v-for="item in hasChildren()" 
        :key="item.path"
        >
            <template #title>
                <el-icon>
                    <component :is="item.icon"></component>
                </el-icon>
                <span>{{ item.label }}</span>
            </template>
            <el-menu-item-group>
                <el-menu-item :index="subItem.path" 
                v-for="(subItem, subIndex) in item.children" 
                :key="subIndex"
                @click="clickMenu(subItem)"
                >
                    <el-icon>
                        <component :is="subItem.icon"></component>
                    </el-icon>
                    <span>{{ subItem.label }}</span>
                </el-menu-item>
            </el-menu-item-group>
        </el-sub-menu>
    </el-menu>
</template>

<script>
    import { useRouter } from "vue-router"
    import { useStore} from 'vuex'
    export default {
        setup() {
            const store = useStore()
            const router = useRouter()
            const list = [
                {
                    path: "/home",
                    name: "home",
                    label: "首页",
                    icon: "s-home",
                    url: "Home/Home",
                },
                {
                    path: "/peo",
                    name: "peo",
                    label: "评分管理",
                    icon: "user",
                    url: "UserManage/UserManage",
                },
                {
                    label: "主要功能",
                    icon: "location",
                    children: [
                        {
                            path: "/userCen",
                            name: "userCen",
                            label: "个人中心",
                            icon: "setting",
                            url: "Other/PageOne",
                        },
                        {
                            path: "/rate",
                            name: "rate",
                            label: "评分",
                            icon: "setting",
                            url: "Other/PageOne",
                        },
                        {
                            path: "/page2",
                            name: "page2",
                            label: "上传",
                            icon: "setting",
                            url: "Other/PageTwo",
                        },
                    ],
                },
            ]

            // filter函数过滤出没有子节点的一级菜单
            const noChildren = () => {
                return list.filter((item) => !item.children)
            }

            // 过滤出有子节点的一级菜单
            const hasChildren = () => {
                return list.filter((item) => item.children)
            }

            // 点击菜单选项切换路由
            const clickMenu = (item) => {
                router.push({
                    name: item.name,
                });
                // vuex管理
                store.commit('selectMenu', item)
            }

            return {
                noChildren,
                hasChildren,
                clickMenu
            }
        }
    }
</script>

<style>
    .el-menu {
        border-right: none;
    }

    .el-menu > h3 {
        line-height: 48px;
        color: #fff;
        text-align: center;
    }
</style>