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

function changeReq(ids, url) {
    modifyReq(url, {ids: ids}, false)
}

function changeDel(url,tableId){
    layer.confirm('真的删除行么?', function (index) {
        let selectData = layui.table.checkStatus(tableId).data;
        if (selectData.length > 0) {
            let idsArray = [];
            for (let i = 0; i < selectData.length; i++) {
                idsArray.push(selectData[i].id);
            }
            let ids = idsArray.toString();
            changeReq(ids, url);
        } else {
            layer.msg("请选中要删除的对象", {icon: 5, time: 2000});
        }
        layer.close(index);
    });
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
                layui.layer.msg(data.msg, {icon: 6, time: 2000}, function () {
                    if (isReload) {
                        parent.layui.table.reload(parent.document.querySelector('table').id);
                    }
                    parent.layer.close(parent.layer.getFrameIndex(window.name));
                });
            } else {
                layer.msg(data.msg, {icon: 5, time: 2000});
            }
        },
        error: function (event) {
            if (event.status == 401) {
                layer.msg("登录超时", {icon: 5, time: 2000});
                parent.window.location.reload(true);//刷新当前页
            } else {
                let json = JSON.parse(event.responseText)
                layer.msg(json.msg, {icon: 5, time: 2000});
            }
        }
    });
}

layui.config({
    base: rootPath
}).extend({
    yadmin: 'layui/extend/yadmin',
    cron: 'layui/extend/cron/cron',
    dtree: 'layui/extend/dtree/dtree',
    tablePlug: 'layui/extend/tablePlug',
    tableTree: 'layui/extend/tableTree',
    tabRightMenu: 'layui/extend/tabRightMenu',
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
});