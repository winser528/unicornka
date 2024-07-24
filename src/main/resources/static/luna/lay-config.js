window.rootPath = (function (src) {
    src = document.scripts[document.scripts.length - 1].src;
    return src.substring(0, src.lastIndexOf("/") + 1);
})();

/**
 * 复制到剪切板
 * @param txt_str
 */
function copy_to_clipboard(txt_str) {
    const input = document.createElement('input');
    document.body.appendChild(input);
    input.setAttribute('value', txt_str);
    input.select();
    if (document.execCommand('copy')) {
        document.execCommand('copy');
        layer.msg('复制成功', {icon: 6, time: 1000});
    }
    document.body.removeChild(input);
}

function editView(url, title) {
    openView(url, title, '40%', '65%', false);
}

/**
 * 弹出窗口
 * @param url 访问地址
 * @param title 标题
 * @param widthParam 窗口宽度
 * @param heightParam 窗口高度
 * @param isFull 是否最大化
 */
function openView(url, title, widthParam, heightParam, isFull) {
    let viewIndex = layer.open({
        content: url,
        title: title,
        area: [widthParam, heightParam],
        type: 2,
        fix: false, //不固定
        maxmin: false,
        shadeClose: true
    });
    if (isFull) {
        layer.full(viewIndex);
    }
}

function errors(answer) {
    if (answer.status == 401) {
        layer.msg("登录超时", {icon: 5, time: 2000});
        parent.window.location.reload(true);//刷新当前页
    } else {
        let json = JSON.parse(answer.responseText)
        layer.msg(json.msg, {icon: 5, time: 2000});
    }
}

function changeReq(ids, url) {
    modifyReq(url, {ids: ids}, false)
}

/**
 *
 * @param url 请求路径
 * @param dataParam 参数
 * @param isReload 是否刷新父页
 */
function modifyReq(url, dataParam, isReload) {
    layui.jquery.ajax({
        type: 'post',
        url: url,
        dataType: 'json',
        data: dataParam,
        success: function (data) {
            if (data.code === 0) {
                layui.layer.msg(data.msg, {icon: 6, time: 1000}, function () {
                    if (isReload) {
                        parent.location.replace(parent.location.href);
                    }
                    parent.layer.close(parent.layer.getFrameIndex(window.name));
                });
            } else {
                if (data.status === 401) {
                    layer.msg("登录超时", {icon: 5, time: 2000});
                    parent.window.location.reload(true);//刷新当前页
                } else {
                    layui.layer.msg(data.msg, {icon: 5, time: 2000});
                }
            }
        },
        error: function (event) {
            errors(event);
        }
    });
}

layui.config({
    base: rootPath
}).extend({
    tabRightMenu: 'layui/extend/tabRightMenu',
    yadmin: 'layui/extend/yadmin',
    dtree: 'layui/extend/dtree/dtree',
    tablePlug: 'layui/extend/tablePlug',
    tableTree: 'layui/extend/tableTree',
    iconPicker: 'layui/extend/icon/iconPicker',
}).use(["jquery", "tabRightMenu", "yadmin"], function ($, tabRightMenu) {
    // 渲染 tab 右键菜单.
    tabRightMenu.render({
        filter: "lay-tab",
        pintabIDs: ["main", "home"],
        width: 110,
    });

    $("body").on("click", function (event) {
        $("div[dtree-id][dtree-select]").removeClass("layui-form-selected");
        $("div[dtree-id][dtree-card]").removeClass("dtree-select-show layui-anim layui-anim-upbit");
    });

    // 对Date的扩展，将 Date 转化为指定格式的String
    // 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
    // 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
    // 例子：
    // (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
    // (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
    Date.prototype.Format = function (fmt) {
        let o = {
            "M+": this.getMonth() + 1, //月份
            "d+": this.getDate(), //日
            "H+": this.getHours(), //小时
            "m+": this.getMinutes(), //分
            "s+": this.getSeconds(), //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度
            "S": this.getMilliseconds() //毫秒
        };
        if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (let k in o)
            if (new RegExp("(" + k + ")").test(fmt)) {
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
            }
        return fmt;
    };
});