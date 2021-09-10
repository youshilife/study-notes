'use strict';

// 后端主机
const BACKEND_HOST = '//localhost:8080'
// API列表
const APIS = {
    getArticle:    BACKEND_HOST + '/api/article/get',
    createArticle: BACKEND_HOST + '/api/article/create',
    updateArticle: BACKEND_HOST + '/api/article/update',
    deleteArticle: BACKEND_HOST + '/api/article/delete',
};

const APP = {
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
            // 正在编辑
            isEditing: false,

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
                .get(APIS.getArticle, {
                    params: {
                        path: window.location.pathname,
                    },
                })
                .then(response => {
                    this.article = response.data.data.article;
                    this.parent = response.data.data.parent;
                    this.children = response.data.data.children;
                })
                .catch(error => {
                    if (error.response) {
                        alert(error.response.data.message);
                    }
                });
        },

        /**
         * 创建文章
         */
        createArticle() {
            let title = prompt('请输入文章标题：');
            if (title === null) {
                return;
            }
            title = title.trim();
            if (title === '') {
                alert('标题不能为空！');
                return;
            }

            let params = new URLSearchParams();
            params.append('parent_id', this.article.id);
            params.append('title',     title);
            axios
                .post(APIS.createArticle, params)
                .then(response => {
                    this.getArticle();
                })
                .catch(error => {
                    if (error.response) {
                        if (error.response.data.message.indexOf('标题冲突') >= 0) {
                            alert(`指定的标题"${title}"在当前分类中已存在，请重新指定标题！`);
                        } else {
                            alert(error.response.data.message);
                        }
                    }
                });
        },

        /**
         * 重新排序文章
         *
         * @param article 要重设排序值的文章
         */
        resortArticle(article) {
            let sortCode = prompt(`文章《${article.title}》当前的排序值为${article.sortCode}，请输入新排序值：`);
            if (sortCode === null) {
                return;
            }
            sortCode = Number(sortCode);
            if (isNaN(sortCode) || Math.floor(sortCode) !== sortCode) {
                alert('排序值必须是一个整数！');
                return;
            }

            let params = new URLSearchParams();
            params.append('id',        article.id);
            params.append('sort_code', sortCode);
            axios
                .post(APIS.updateArticle, params)
                .then(response => {
                    this.getArticle();
                })
                .catch(error => {
                    if (error.response) {
                        alert(error.response.data.message);
                    }
                });
        },

        /**
         * 删除文章
         *
         * @param article 要删除的文章
         */
        deleteArticle(article) {
            if (confirm(`是否确定删除文章《${article.title}》及其所有子文章？`)) {
                let params = new URLSearchParams();
                params.append('id', article.id);
                axios
                    .post(APIS.deleteArticle, params)
                    .then(response => {
                        this.getArticle();
                    })
                    .catch(error => {
                        if (error.response) {
                            alert(error.response.data.message);
                        }
                    });
            }
        },

        /**
         * 编辑文章
         */
        editArticle() {
            this.article.contentBackup = this.article.content;
            this.isEditing = true;
        },

        /**
         * 取消编辑文章
         */
        cancelEditArticle() {
            this.isEditing = false;
            this.article.content = this.article.contentBackup;
        },

        /**
         * 更新文章内容
         */
        updateArticleContent() {
            let params = new URLSearchParams();
            params.append('id',      this.article.id);
            params.append('content', this.article.content);
            axios
                .post(APIS.updateArticle, params)
                .then(response => {
                    this.isEditing = false;
                    this.getArticle();
                })
                .catch(error => {
                    if (error.response) {
                        alert(error.response.data.message);
                    }
                });
        },
    },

    mounted() {
        this.getArticle();
    },
};

const VUE_APP = Vue.createApp(APP);
VUE_APP.mount('#app');
