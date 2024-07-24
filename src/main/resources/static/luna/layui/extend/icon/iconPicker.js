/**
 * @name iconSelected
 * @author HuangJunjie
 * @description layui 图标选择器
 * @version 1.0.0.20210605
 */
layui.define(["layer", "jquery"], function (exports) {
    var $ = layui.jquery;
    var prefix = "layui-ext-icon-selected";
    layui.link(layui.cache.modules["iconPicker"].replace('js', 'css?'));

    // Layui默认图标字典
    var layuiIcons = [
        {
            "classList": "layui-icon layui-icon-heart-fill",
            "name": "实心",
            "code": "&#xe68f;"
        },
        {
            "classList": "layui-icon layui-icon-heart",
            "name": "空心",
            "code": "&#xe68c;"
        },
        {
            "classList": "layui-icon layui-icon-light",
            "name": "亮度/晴",
            "code": "&#xe748;"
        },
        {
            "classList": "layui-icon layui-icon-time",
            "name": "时间/历史",
            "code": "&#xe68d;"
        },
        {
            "classList": "layui-icon layui-icon-bluetooth",
            "name": "蓝牙",
            "code": "&#xe689;"
        },
        {
            "classList": "layui-icon layui-icon-at",
            "name": "@艾特",
            "code": "&#xe687;"
        },
        {
            "classList": "layui-icon layui-icon-mute",
            "name": "静音",
            "code": "&#xe685;"
        },
        {
            "classList": "layui-icon layui-icon-mike",
            "name": "录音/麦克风",
            "code": "&#xe6dc;"
        },
        {
            "classList": "layui-icon layui-icon-key",
            "name": "密钥/钥匙",
            "code": "&#xe683;"
        },
        {
            "classList": "layui-icon layui-icon-gift",
            "name": "礼物/活动",
            "code": "&#xe627;"
        },
        {
            "classList": "layui-icon layui-icon-email",
            "name": "邮箱",
            "code": "&#xe618;"
        },
        {
            "classList": "layui-icon layui-icon-rss",
            "name": "RSS",
            "code": "&#xe808;"
        },
        {
            "classList": "layui-icon layui-icon-wifi",
            "name": "WiFi",
            "code": "&#xe7e0;"
        },
        {
            "classList": "layui-icon layui-icon-logout",
            "name": "退出/注销",
            "code": "&#xe682;"
        },
        {
            "classList": "layui-icon layui-icon-android",
            "name": "Android 安卓",
            "code": "&#xe684;"
        },
        {
            "classList": "layui-icon layui-icon-ios",
            "name": "Apple IOS 苹果",
            "code": "&#xe680;"
        },
        {
            "classList": "layui-icon layui-icon-windows",
            "name": "Windows",
            "code": "&#xe67f;"
        },
        {
            "classList": "layui-icon layui-icon-transfer",
            "name": "穿梭框",
            "code": "&#xe691;"
        },
        {
            "classList": "layui-icon layui-icon-service",
            "name": "客服",
            "code": "&#xe626;"
        },
        {
            "classList": "layui-icon layui-icon-subtraction",
            "name": "减",
            "code": "&#xe67e;"
        },
        {
            "classList": "layui-icon layui-icon-addition",
            "name": "加",
            "code": "&#xe624;"
        },
        {
            "classList": "layui-icon layui-icon-slider",
            "name": "滑块",
            "code": "&#xe714;"
        },
        {
            "classList": "layui-icon layui-icon-print",
            "name": "打印",
            "code": "&#xe66d;"
        },
        {
            "classList": "layui-icon layui-icon-export",
            "name": "导出",
            "code": "&#xe67d;"
        },
        {
            "classList": "layui-icon layui-icon-cols",
            "name": "列",
            "code": "&#xe610;"
        },
        {
            "classList": "layui-icon layui-icon-screen-restore",
            "name": "退出全屏",
            "code": "&#xe758;"
        },
        {
            "classList": "layui-icon layui-icon-screen-full",
            "name": "全屏",
            "code": "&#xe622;"
        },
        {
            "classList": "layui-icon layui-icon-rate-half",
            "name": "半星",
            "code": "&#xe6c9;"
        },
        {
            "classList": "layui-icon layui-icon-rate",
            "name": "星星-空心",
            "code": "&#xe67b;"
        },
        {
            "classList": "layui-icon layui-icon-rate-solid",
            "name": "星星-实心",
            "code": "&#xe67a;"
        },
        {
            "classList": "layui-icon layui-icon-cellphone",
            "name": "手机",
            "code": "&#xe678;"
        },
        {
            "classList": "layui-icon layui-icon-vercode",
            "name": "验证码",
            "code": "&#xe679;"
        },
        {
            "classList": "layui-icon layui-icon-login-wechat",
            "name": "微信",
            "code": "&#xe677;"
        },
        {
            "classList": "layui-icon layui-icon-login-qq",
            "name": "QQ",
            "code": "&#xe676;"
        },
        {
            "classList": "layui-icon layui-icon-login-weibo",
            "name": "微博",
            "code": "&#xe675;"
        },
        {
            "classList": "layui-icon layui-icon-password",
            "name": "密码",
            "code": "&#xe673;"
        },
        {
            "classList": "layui-icon layui-icon-username",
            "name": "用户名",
            "code": "&#xe66f;"
        },
        {
            "classList": "layui-icon layui-icon-refresh-3",
            "name": "刷新-粗",
            "code": "&#xe9aa;"
        },
        {
            "classList": "layui-icon layui-icon-auz",
            "name": "授权",
            "code": "&#xe672;"
        },
        {
            "classList": "layui-icon layui-icon-spread-left",
            "name": "左向右伸缩菜单",
            "code": "&#xe66b;"
        },
        {
            "classList": "layui-icon layui-icon-shrink-right",
            "name": "右向左伸缩菜单",
            "code": "&#xe668;"
        },
        {
            "classList": "layui-icon layui-icon-snowflake",
            "name": "雪花",
            "code": "&#xe6b1;"
        },
        {
            "classList": "layui-icon layui-icon-tips",
            "name": "提示说明",
            "code": "&#xe702;"
        },
        {
            "classList": "layui-icon layui-icon-note",
            "name": "便签",
            "code": "&#xe66e;"
        },
        {
            "classList": "layui-icon layui-icon-home",
            "name": "主页",
            "code": "&#xe68e;"
        },
        {
            "classList": "layui-icon layui-icon-senior",
            "name": "高级",
            "code": "&#xe674;"
        },
        {
            "classList": "layui-icon layui-icon-refresh",
            "name": "刷新",
            "code": "&#xe669;"
        },
        {
            "classList": "layui-icon layui-icon-refresh-1",
            "name": "刷新",
            "code": "&#xe666;"
        },
        {
            "classList": "layui-icon layui-icon-flag",
            "name": "旗帜",
            "code": "&#xe66c;"
        },
        {
            "classList": "layui-icon layui-icon-theme",
            "name": "主题",
            "code": "&#xe66a;"
        },
        {
            "classList": "layui-icon layui-icon-notice",
            "name": "消息-通知",
            "code": "&#xe667;"
        },
        {
            "classList": "layui-icon layui-icon-website",
            "name": "网站",
            "code": "&#xe7ae;"
        },
        {
            "classList": "layui-icon layui-icon-console",
            "name": "控制台",
            "code": "&#xe665;"
        },
        {
            "classList": "layui-icon layui-icon-face-surprised",
            "name": "表情-惊讶",
            "code": "&#xe664;"
        },
        {
            "classList": "layui-icon layui-icon-set",
            "name": "设置-空心",
            "code": "&#xe716;"
        },
        {
            "classList": "layui-icon layui-icon-template-1",
            "name": "模板",
            "code": "&#xe656;"
        },
        {
            "classList": "layui-icon layui-icon-app",
            "name": "应用",
            "code": "&#xe653;"
        },
        {
            "classList": "layui-icon layui-icon-template",
            "name": "模板",
            "code": "&#xe663;"
        },
        {
            "classList": "layui-icon layui-icon-praise",
            "name": "赞",
            "code": "&#xe6c6;"
        },
        {
            "classList": "layui-icon layui-icon-tread",
            "name": "踩",
            "code": "&#xe6c5;"
        },
        {
            "classList": "layui-icon layui-icon-male",
            "name": "男",
            "code": "&#xe662;"
        },
        {
            "classList": "layui-icon layui-icon-female",
            "name": "女",
            "code": "&#xe661;"
        },
        {
            "classList": "layui-icon layui-icon-camera",
            "name": "相机-空心",
            "code": "&#xe660;"
        },
        {
            "classList": "layui-icon layui-icon-camera-fill",
            "name": "相机-实心",
            "code": "&#xe65d;"
        },
        {
            "classList": "layui-icon layui-icon-more",
            "name": "菜单-水平",
            "code": "&#xe65f;"
        },
        {
            "classList": "layui-icon layui-icon-more-vertical",
            "name": "菜单-垂直",
            "code": "&#xe671;"
        },
        {
            "classList": "layui-icon layui-icon-rmb",
            "name": "金额-人民币",
            "code": "&#xe65e;"
        },
        {
            "classList": "layui-icon layui-icon-dollar",
            "name": "金额-美元",
            "code": "&#xe659;"
        },
        {
            "classList": "layui-icon layui-icon-diamond",
            "name": "钻石-等级",
            "code": "&#xe735;"
        },
        {
            "classList": "layui-icon layui-icon-fire",
            "name": "火",
            "code": "&#xe756;"
        },
        {
            "classList": "layui-icon layui-icon-return",
            "name": "返回",
            "code": "&#xe65c;"
        },
        {
            "classList": "layui-icon layui-icon-location",
            "name": "位置-地图",
            "code": "&#xe715;"
        },
        {
            "classList": "layui-icon layui-icon-read",
            "name": "办公-阅读",
            "code": "&#xe705;"
        },
        {
            "classList": "layui-icon layui-icon-survey",
            "name": "调查",
            "code": "&#xe6b2;"
        },
        {
            "classList": "layui-icon layui-icon-face-smile",
            "name": "表情-微笑",
            "code": "&#xe6af;"
        },
        {
            "classList": "layui-icon layui-icon-face-cry",
            "name": "表情-哭泣",
            "code": "&#xe69c;"
        },
        {
            "classList": "layui-icon layui-icon-cart-simple",
            "name": "购物车",
            "code": "&#xe698;"
        },
        {
            "classList": "layui-icon layui-icon-cart",
            "name": "购物车",
            "code": "&#xe657;"
        },
        {
            "classList": "layui-icon layui-icon-next",
            "name": "下一页",
            "code": "&#xe65b;"
        },
        {
            "classList": "layui-icon layui-icon-prev",
            "name": "上一页",
            "code": "&#xe65a;"
        },
        {
            "classList": "layui-icon layui-icon-upload-drag",
            "name": "上传-空心-拖拽",
            "code": "&#xe681;"
        },
        {
            "classList": "layui-icon layui-icon-upload",
            "name": "上传-实心",
            "code": "&#xe67c;"
        },
        {
            "classList": "layui-icon layui-icon-download-circle",
            "name": "下载-圆圈",
            "code": "&#xe601;"
        },
        {
            "classList": "layui-icon layui-icon-component",
            "name": "组件",
            "code": "&#xe857;"
        },
        {
            "classList": "layui-icon layui-icon-file-b",
            "name": "文件-粗",
            "code": "&#xe655;"
        },
        {
            "classList": "layui-icon layui-icon-user",
            "name": "用户",
            "code": "&#xe770;"
        },
        {
            "classList": "layui-icon layui-icon-find-fill",
            "name": "发现-实心",
            "code": "&#xe670;"
        },
        {
            "classList": "layui-icon layui-icon-loading layui-anim layui-anim-rotate layui-anim-loop",
            "name": "loading",
            "code": "&#xe63d;"
        },
        {
            "classList": "layui-icon layui-icon-loading-1 layui-anim layui-anim-rotate layui-anim-loop",
            "name": "loading",
            "code": "&#xe63e;"
        },
        {
            "classList": "layui-icon layui-icon-add-1",
            "name": "添加",
            "code": "&#xe654;"
        },
        {
            "classList": "layui-icon layui-icon-play",
            "name": "播放",
            "code": "&#xe652;"
        },
        {
            "classList": "layui-icon layui-icon-pause",
            "name": "暂停",
            "code": "&#xe651;"
        },
        {
            "classList": "layui-icon layui-icon-headset",
            "name": "音频-耳机",
            "code": "&#xe6fc;"
        },
        {
            "classList": "layui-icon layui-icon-video",
            "name": "视频",
            "code": "&#xe6ed;"
        },
        {
            "classList": "layui-icon layui-icon-voice",
            "name": "语音-声音",
            "code": "&#xe688;"
        },
        {
            "classList": "layui-icon layui-icon-speaker",
            "name": "消息-通知-喇叭",
            "code": "&#xe645;"
        },
        {
            "classList": "layui-icon layui-icon-fonts-del",
            "name": "删除线",
            "code": "&#xe64f;"
        },
        {
            "classList": "layui-icon layui-icon-fonts-code",
            "name": "代码",
            "code": "&#xe64e;"
        },
        {
            "classList": "layui-icon layui-icon-fonts-html",
            "name": "HTML",
            "code": "&#xe64b;"
        },
        {
            "classList": "layui-icon layui-icon-fonts-strong",
            "name": "字体加粗",
            "code": "&#xe62b;"
        },
        {
            "classList": "layui-icon layui-icon-unlink",
            "name": "删除链接",
            "code": "&#xe64d;"
        },
        {
            "classList": "layui-icon layui-icon-picture",
            "name": "图片",
            "code": "&#xe64a;"
        },
        {
            "classList": "layui-icon layui-icon-link",
            "name": "链接",
            "code": "&#xe64c;"
        },
        {
            "classList": "layui-icon layui-icon-face-smile-b",
            "name": "表情-笑-粗",
            "code": "&#xe650;"
        },
        {
            "classList": "layui-icon layui-icon-align-left",
            "name": "左对齐",
            "code": "&#xe649;"
        },
        {
            "classList": "layui-icon layui-icon-align-right",
            "name": "右对齐",
            "code": "&#xe648;"
        },
        {
            "classList": "layui-icon layui-icon-align-center",
            "name": "居中对齐",
            "code": "&#xe647;"
        },
        {
            "classList": "layui-icon layui-icon-fonts-u",
            "name": "字体-下划线",
            "code": "&#xe646;"
        },
        {
            "classList": "layui-icon layui-icon-fonts-i",
            "name": "字体-斜体",
            "code": "&#xe644;"
        },
        {
            "classList": "layui-icon layui-icon-tabs",
            "name": "Tabs 选项卡",
            "code": "&#xe62a;"
        },
        {
            "classList": "layui-icon layui-icon-radio",
            "name": "单选框-选中",
            "code": "&#xe643;"
        },
        {
            "classList": "layui-icon layui-icon-circle",
            "name": "单选框-候选",
            "code": "&#xe63f;"
        },
        {
            "classList": "layui-icon layui-icon-edit",
            "name": "编辑",
            "code": "&#xe642;"
        },
        {
            "classList": "layui-icon layui-icon-share",
            "name": "分享",
            "code": "&#xe641;"
        },
        {
            "classList": "layui-icon layui-icon-delete",
            "name": "删除",
            "code": "&#xe640;"
        },
        {
            "classList": "layui-icon layui-icon-form",
            "name": "表单",
            "code": "&#xe63c;"
        },
        {
            "classList": "layui-icon layui-icon-cellphone-fine",
            "name": "手机-细体",
            "code": "&#xe63b;"
        },
        {
            "classList": "layui-icon layui-icon-dialogue",
            "name": "聊天 对话 沟通",
            "code": "&#xe63a;"
        },
        {
            "classList": "layui-icon layui-icon-fonts-clear",
            "name": "文字格式化",
            "code": "&#xe639;"
        },
        {
            "classList": "layui-icon layui-icon-layer",
            "name": "窗口",
            "code": "&#xe638;"
        },
        {
            "classList": "layui-icon layui-icon-date",
            "name": "日期",
            "code": "&#xe637;"
        },
        {
            "classList": "layui-icon layui-icon-water",
            "name": "水 下雨",
            "code": "&#xe636;"
        },
        {
            "classList": "layui-icon layui-icon-code-circle",
            "name": "代码-圆圈",
            "code": "&#xe635;"
        },
        {
            "classList": "layui-icon layui-icon-carousel",
            "name": "轮播组图",
            "code": "&#xe634;"
        },
        {
            "classList": "layui-icon layui-icon-prev-circle",
            "name": "翻页",
            "code": "&#xe633;"
        },
        {
            "classList": "layui-icon layui-icon-layouts",
            "name": "布局",
            "code": "&#xe632;"
        },
        {
            "classList": "layui-icon layui-icon-util",
            "name": "工具",
            "code": "&#xe631;"
        },
        {
            "classList": "layui-icon layui-icon-templeate-1",
            "name": "选择模板",
            "code": "&#xe630;"
        },
        {
            "classList": "layui-icon layui-icon-upload-circle",
            "name": "上传-圆圈",
            "code": "&#xe62f;"
        },
        {
            "classList": "layui-icon layui-icon-tree",
            "name": "树",
            "code": "&#xe62e;"
        },
        {
            "classList": "layui-icon layui-icon-table",
            "name": "表格",
            "code": "&#xe62d;"
        },
        {
            "classList": "layui-icon layui-icon-chart",
            "name": "图表",
            "code": "&#xe62c;"
        },
        {
            "classList": "layui-icon layui-icon-chart-screen",
            "name": "图标 报表 屏幕",
            "code": "&#xe629;"
        },
        {
            "classList": "layui-icon layui-icon-engine",
            "name": "引擎",
            "code": "&#xe628;"
        },
        {
            "classList": "layui-icon layui-icon-triangle-d",
            "name": "下三角",
            "code": "&#xe625;"
        },
        {
            "classList": "layui-icon layui-icon-triangle-r",
            "name": "右三角",
            "code": "&#xe623;"
        },
        {
            "classList": "layui-icon layui-icon-file",
            "name": "文件",
            "code": "&#xe621;"
        },
        {
            "classList": "layui-icon layui-icon-set-sm",
            "name": "设置-小型",
            "code": "&#xe620;"
        },
        {
            "classList": "layui-icon layui-icon-reduce-circle",
            "name": "减少-圆圈",
            "code": "&#xe616;"
        },
        {
            "classList": "layui-icon layui-icon-add-circle",
            "name": "添加-圆圈",
            "code": "&#xe61f;"
        },
        {
            "classList": "layui-icon layui-icon-404",
            "name": "404",
            "code": "&#xe61c;"
        },
        {
            "classList": "layui-icon layui-icon-about",
            "name": "关于",
            "code": "&#xe60b;"
        },
        {
            "classList": "layui-icon layui-icon-up",
            "name": "箭头 向上",
            "code": "&#xe619;"
        },
        {
            "classList": "layui-icon layui-icon-down",
            "name": "箭头 向下",
            "code": "&#xe61a;"
        },
        {
            "classList": "layui-icon layui-icon-left",
            "name": "箭头 向左",
            "code": "&#xe603;"
        },
        {
            "classList": "layui-icon layui-icon-right",
            "name": "箭头 向右",
            "code": "&#xe602;"
        },
        {
            "classList": "layui-icon layui-icon-circle-dot",
            "name": "圆点",
            "code": "&#xe617;"
        },
        {
            "classList": "layui-icon layui-icon-search",
            "name": "搜索",
            "code": "&#xe615;"
        },
        {
            "classList": "layui-icon layui-icon-set-fill",
            "name": "设置-实心",
            "code": "&#xe614;"
        },
        {
            "classList": "layui-icon layui-icon-group",
            "name": "群组",
            "code": "&#xe613;"
        },
        {
            "classList": "layui-icon layui-icon-friends",
            "name": "好友",
            "code": "&#xe612;"
        },
        {
            "classList": "layui-icon layui-icon-reply-fill",
            "name": "回复 评论 实心",
            "code": "&#xe611;"
        },
        {
            "classList": "layui-icon layui-icon-menu-fill",
            "name": "菜单 隐身 实心",
            "code": "&#xe60f;"
        },
        {
            "classList": "layui-icon layui-icon-log",
            "name": "记录",
            "code": "&#xe60e;"
        },
        {
            "classList": "layui-icon layui-icon-picture-fine",
            "name": "图片-细体",
            "code": "&#xe60d;"
        },
        {
            "classList": "layui-icon layui-icon-face-smile-fine",
            "name": "表情-笑-细体",
            "code": "&#xe60c;"
        },
        {
            "classList": "layui-icon layui-icon-list",
            "name": "列表",
            "code": "&#xe60a;"
        },
        {
            "classList": "layui-icon layui-icon-release",
            "name": "发布 纸飞机",
            "code": "&#xe609;"
        },
        {
            "classList": "layui-icon layui-icon-ok",
            "name": "对 OK",
            "code": "&#xe605;"
        },
        {
            "classList": "layui-icon layui-icon-help",
            "name": "帮助",
            "code": "&#xe607;"
        },
        {
            "classList": "layui-icon layui-icon-chat",
            "name": "客服",
            "code": "&#xe606;"
        },
        {
            "classList": "layui-icon layui-icon-top",
            "name": "top 置顶",
            "code": "&#xe604;"
        },
        {
            "classList": "layui-icon layui-icon-star",
            "name": "收藏-空心",
            "code": "&#xe600;"
        },
        {
            "classList": "layui-icon layui-icon-star-fill",
            "name": "收藏-实心",
            "code": "&#xe658;"
        },
        {
            "classList": "layui-icon layui-icon-close-fill",
            "name": "关闭-实心",
            "code": "&#x1007;"
        },
        {
            "classList": "layui-icon layui-icon-close",
            "name": "关闭-空心",
            "code": "&#x1006;"
        },
        {
            "classList": "layui-icon layui-icon-ok-circle",
            "name": "正确",
            "code": "&#x1005;"
        },
        {
            "classList": "layui-icon layui-icon-add-circle-fine",
            "name": "添加-圆圈-细体",
            "code": "&#xe608;"
        }
    ];

    // 生成样式
    function generatorClass(className) {
        return [prefix, className].join("-");
    }

    // 入口
    function init(id, opts) {
        if (!opts) opts = {};

        // 初始化必要DOM
        var $input = $(id);
        $input.hide();
        var $body = $("body");
        var $parent = $input.parent();

        // 初始化配置
        var width = opts.width || 300;
        var offsetX = opts.offsetX || 30;
        var offsetY = opts.offsetY || 10;
        var icons = opts.icons || layuiIcons;
        var placeholder = $input.attr("placeholder") || opts.placeholder || "请选择";
        var maxWidth = $body.width();
        var value = $input.val() || opts.value;

        // 托管事件
        function activeEvent(name, event, data) {
            if (opts && opts.event && typeof opts.event[name] == "function") {
                opts.event[name](event, data);
            }
        }

        // 更新值
        function updateValueByClassList(classList) {
            for (var i = 0; i < icons.length; i++) {
                var icon = icons[i];

                if (icon.classList === classList) {
                    // 处理选中效果
                    var $icons = $icon_container.find("." + generatorClass("item"));
                    $icons.removeClass("selected");
                    $icons.eq(i).addClass("selected");

                    // 清空输入框
                    $input_dom.empty();

                    // 设置值
                    var $select = $('<div class="' + generatorClass("selected-value") + '"></div>');
                    var $i = $('<i class="' + icon.classList + '"></i>').addClass(generatorClass("icon"));
                    var $name = $('<div class="' + generatorClass("name") + '">' + icon.name + '</div>');
                    $select.append($i).append($name);
                    $input_dom.append($select).append($icon_down);
                    $icon_container.removeClass("show");
                    $input.val(classList);
                    break;
                }
            }
        }

        // 动态计算弹窗偏移量
        var leftPx = $parent.offset().left;
        var topPx = $parent.offset().top + $parent.height() + offsetY;
        if (leftPx + width > maxWidth) {
            leftPx = leftPx - (leftPx + width - maxWidth) - offsetX;
        }

        // 创建虚拟DOM
        var $placeholder = $('<span class="placeholder">' + placeholder + '</span>');
        var $input_dom = $('<div class="' + generatorClass('input') + '"></div>');
        var $icon_down = $('<i class="layui-icon layui-icon-triangle-d"></i>');
        var $icon_container = $('<div class="' + generatorClass("container") + '"></div>');
        $icon_container.css({
            left: leftPx,
            top: topPx
        });
        $input_dom.append($placeholder).append($icon_down);
        $parent.append($input_dom);
        $body.append($icon_container);

        $body.click(function () {
            $icon_container.removeClass("show")
        });

        $input_dom.click(function (e) {
            e.stopPropagation();
            $input_dom.hasClass("show") ? $input_dom.removeClass("show") : $input_dom.addClass("show");
            $icon_container.hasClass("show") ? $icon_container.removeClass("show") : $icon_container.addClass("show");
        });

        if (!opts) opts = {};

        icons.forEach(function (icon, index) {
            var $icon = $('<div class="' + generatorClass("item") + '"></div>');
            var $i = $('<i class="' + icon.classList + '"></i>').addClass(generatorClass("icon"));
            var $name = $('<div class="' + generatorClass("name") + '">' + icon.name + '</div>');
            $icon.append($i).append($name).click(function (e) {
                e.stopPropagation();
                e.preventDefault();

                var classList = icons[index].classList;
                updateValueByClassList(classList);
                activeEvent("select", e, {
                    index: index,
                    icons: icons,
                    icon: classList
                })
            });

            $icon_container.append($icon);
        });

        updateValueByClassList(value);
    }

    exports("iconPicker", {
        init: init
    })
})