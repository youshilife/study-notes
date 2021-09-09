'use strict';

// 后端主机
const backendHost = '//localhost:8080'
// API列表
const apis = {
    getArticle:    backendHost + '/api/article',
    createArticle: backendHost + '/api/article/create',
    updateArticle: backendHost + '/api/article/update',
    resortArticle: backendHost + '/api/article/resort',
    moveArticle:   backendHost + '/api/article/move',
    deleteArticle: backendHost + '/api/article/delete',
};

let app = {
    data() {
        return {
            // 当前文章
            article: {},
            // 父文章
            parent: {},
            // 子文章
            children: [],

            // 侧边栏是否显示
            isSidebarShown: false,

            // Markdown渲染器
            markdownRenderer: window.markdownit({
                html: true,
            }),
        };
    },

    methods: {
        /**
         * 渲染Markdown为HTML
         *
         * @param {string} markdown Markdown代码
         * @returns {string}        HTML代码
         */
        renderMarkdown(markdown) {
            return this.markdownRenderer.render(String(markdown));
        },

        /**
         * 显示侧边栏
         */
        showSidebar() {
            this.isSidebarShown = true;
        },

        /**
         * 隐藏侧边栏
         */
        hideSidebar() {
            this.isSidebarShown = false;
        },

        /**
         * 获取文章
         */
        getArticle() {
            axios
                .get(apis.getArticle, {
                    params: {
                        path: window.location.pathname,
                    },
                })
                .then(response => {
                    if (response.data.code === 0) {
                        this.article = response.data.data.article;
                        this.parent = response.data.data.parent;
                        this.children = response.data.data.children;
                    } else {
                        alert(response.data.message);
                    }
                });
        },

        /**
         * 创建文章
         */
        createArticle() {
            let title = prompt("请输入文章标题：");
            if (title === null) {
                return;
            }

            let params = new URLSearchParams();
            params.append("parent_id", this.article.id);
            params.append("title", title);
            axios
                .post(apis.createArticle, params)
                .then(response => {
                    if (response.data.code === 0) {
                        this.getArticle();
                    } else {
                        alert(response.data.message);
                    }
                });
        },
    },

    mounted() {
        this.getArticle();
    },
};

let vueApp = Vue.createApp(app);
vueApp.mount('#app');
