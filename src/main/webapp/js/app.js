'use strict';

let app = {
    data() {
        return {
            // 当前文章
            article: {
                id: 5,
                path: '/计算机/编程语言/Java',
                title: 'Java',
                content: `
## Java的历史

变量\`name\`，**粗体**，*斜体*，<span style="color: red;">内嵌HTML</span>。

\`\`\` java
public static void main(String[] args) {
    System.out.println("你好，world." + 5);
}
\`\`\`

- 苹果
- 梨
- 香蕉
`,
            },
            // 父文章
            parent: {
                id: 3,
                path: '/计算机/编程语言',
                title: '编程语言',
            },
            // 子文章
            children: [
                {
                    id: 12,
                    path: '/计算机/编程语言/Java/数据类型',
                    title: '数据类型',
                },
                {
                    id: 12,
                    path: '/计算机/编程语言/Java/变量',
                    title: '变量',
                },
                {
                    id: 12,
                    path: '/计算机/编程语言/Java/控制结构',
                    title: '控制结构',
                },
                {
                    id: 12,
                    path: '/计算机/编程语言/Java/输出与输入',
                    title: '输出与输入',
                },
                {
                    id: 12,
                    path: '/计算机/编程语言/Java/代码风格',
                    title: '代码风格',
                },
            ],

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
            return this.markdownRenderer.render(markdown);
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
    },
};

let vueApp = Vue.createApp(app);
vueApp.mount('#app');
