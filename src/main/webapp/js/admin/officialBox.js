$(document).ready(function () {
    let reg = /windows/i;
    if (reg.test(navigator.userAgent)) {
        let backup = $('#forBackup'),bm=$('#bk-modal'),im=$('#import-modal'),fi=$('#for-import');
        backup.prop('disabled', true);
        backup.hide();
        bm.hide();
        bm.prop('disabled',true);
        im.hide();
        im.prop('disabled',true);
        fi.prop('disabled',true);
        fi.hide();
    }
    $('#table').bootstrapTable({
        url: '../admin/officialBox/select',
        pagination: true,
        pageNumber: 1, //初始化加载第一页，默认第一页
        pageSize: 5, //每页的记录行数（*）
        pageList: [5, 10, 15],//可供选择的每页行数
        paginationLoop: true,
        sortOrder: "desc",
        sidePagination: 'server',//采用服务器端分页
        responseHandler: function (data) {
            if (0 != data.code) {
                return null;
            }
            return {
                total: data.body.totalElements,
                rows: data.body.content
            };
        },
        queryParamsType: function (params) {
            console.log(params);
            return {
                pageSize: params.limit,   //页面大小
                pageNumber: params.pageNumber,  //页码
            }
        },
        columns: [
            {
                field: 'boxNumber',//域值
                title: '档号',//标题
                align: 'center',
                valign: 'middle',
                visible: true,
                width: '5%'
            },
            {
                field: 'totalNumber',
                title: '文件总数',
                align: 'center',
                valign: 'middle',
                visible: true,
                width: '5%'
            },
            {
                title: '操作',
                align: 'center',
                valign: 'middle',
                events: operateEvents,
                visible: true,
                width: '5%',
                formatter: operateFormatter
            }]
    });
})

$('#bk-modal').click(function () {
    $('#backup').modal({backdrop: 'static', keyboard: false});
})

$('#import-modal').click(function () {
    $('#import').modal({backdrop: 'static', keyboard: false});
})

$('#add-box').click(function () {
    $('#add').modal({backdrop: 'static', keyboard: false});
})


$('#forBackup').click(function () {
    let filepath = $('#path').val();
    if (filepath) {
        $.ajax({
            type: 'post',
            url: '../admin/officialDocument/backups',
            data: {'destPath': filepath},
            beforeSend: function () {
                ShowDiv();
            },
            complete: function () {
                HiddenDiv();
            },
            success: function (data) {
                if (data.code == 0) {
                    alert(data.message);
                    $('#backup').modal('hide');
                    $('#path').val('')
                }
                else {
                    alert(data.message);
                }
            }
        })
    }
    else {
        alert("请填写备份路径！")
    }
})

$('#for-import').click(function () {
    let filepath = $('#import-path').val();
    if (filepath) {
        $.ajax({
            type: 'post',
            url: '../admin/officialDocument/import',
            data: {'importPath': filepath},
            beforeSend: function () {
                ShowDiv();
            },
            complete: function () {
                HiddenDiv();
            },
            success: function (data) {
                if (data.code == 0) {
                    alert(data.message);
                    $('#import').modal('hide');
                    $('#import-path').val('');
                }
                else {
                    alert(data.message);
                }
            }
        })
    }
    else {
        alert("请填写文件夹路径！")
    }
})



//显示加载数据
function ShowDiv() {
    $("#loading").show();
    $('button').prop('disabled',true)
}

//隐藏加载数据
function HiddenDiv() {
    $("#loading").hide();
    $('button').prop('disabled',false)
}

$('#addBox').click(function () {
    var boxNumber = $('#boxNum').val();
    var totalFileNumber = $('#totalFileNum').val();
    if (boxNumber !== "") {
        $.ajax({
            type: 'post',
            url: '../admin/officialBox/add',
            data: {'boxNumber': boxNumber, 'totalNumber': totalFileNumber},
            success: function (data) {
                if (data.code == 0) {
                    alert(data.message);
                    $('#table').bootstrapTable('refresh');
                    $('#add').modal('hide');
                    $('#boxNum').val('');
                    $('#totalFileNum').val('');
                }
                else {
                    alert(data.message);
                }
            }
        })
    }
    else {
        alert("请补全信息！");
    }
})


$('#search').click(function () {
    $('#table').bootstrapTable('destroy')
    $('#table').bootstrapTable({
        url: '../admin/officialBox/select?boxNumber=' + encodeURIComponent($('#con').val()),
        pagination: true,
        pageNumber: 1, //初始化加载第一页，默认第一页
        pageSize: 5, //每页的记录行数（*）
        pageList: [5, 10, 15],//可供选择的每页行数
        paginationLoop: true,
        sortOrder: "desc",
        sidePagination: 'server',//采用服务器端分页
        responseHandler: function (data) {
            if (0 != data.code) {
                return null;
            }
            return {
                total: data.body.totalElements,
                rows: data.body.content
            };
        },
        queryParamsType: function (params) {
            console.log(params);
            return {
                pageSize: params.limit,   //页面大小
                pageNumber: params.pageNumber,  //页码
            }
        },
        columns: [
            {
                field: 'boxNumber',//域值
                title: '档号',//标题
                align: 'center',
                valign: 'middle',
                visible: true,
                width: '5%'
            },
            {
                field: 'totalNumber',
                title: '文件总数',
                align: 'center',
                valign: 'middle',
                visible: true,
                width: '5%'
            },
            {
                title: '操作',
                align: 'center',
                valign: 'middle',
                events: operateEvents,
                visible: true,
                width: '5%',
                formatter: operateFormatter
            }]
    })
})

$('#modify').click(function () {
    $.ajax({
        type: 'post',
        url: '../admin/officialBox/update',
        data: {
            'id': $('#boxId').val(),
            'boxNumber': $('#boxNum-change').val(),
            'totalNumber': $('#totalFileNum-change').val()
        },
        success: function (data) {
            if (data.code == 0) {
                alert(data.message);
                $('#table').bootstrapTable('refresh');
                $('#change').modal('hide');
                $('#boxNum-change').val('');
                $('#totalFileNum-change').val('');
            }
            else {
                alert(data.message);
            }
        }
    })
})


function operateFormatter(value, row, index) {
    return [
        '<a class="change"  href="javascript:void(0)" >',
        '修改',
        '</a>|',
        '<a class="remove" href="javascript:void(0);"  >',
        '删除',
        '</a><br>',
        '<a class="browse"  href="javascript:void(0)" >',
        '查看档案',
        '</a>|',
        '<a  href="../admin/officialDocument/export?officialBoxId=' + row.id + '" >',
        '导出Excel',
        '</a>'
    ].join('');
}

window.operateEvents = {
    'click .change': function (e, value, row, index) {
        $('#change').modal();
        $('#boxId').val(row.id);
        $('#boxNum-change').val(row.boxNumber);
        $('#totalFileNum-change').val(row.totalNumber);
    },
    'click .remove': function (e, value, row, index) {
        if (confirm("您确定要删除该文书档案盒吗？\n该盒子下的所有文件将被删除！")) {
            $.ajax({
                type: "DELETE",
                url: "../admin/officialBox/delete/" + row.id,
                success: function (data) {
                    $("#table").bootstrapTable("refresh");
                }
            });
        }
    },
    'click .browse': function (e, value, row, index) {
        window.location.href = 'officialManage.html?boxId=' + row.id + "&boxNum=" + encodeURIComponent(row.boxNumber);
    }
}

$('#exit').click(function () {
    if (confirm("是否要退出登录?")) {
        $.ajax({
            type: 'get',
            url: '../loginout',
            success: function (data) {
                if (data.code == 0) {
                    alert(data.message);
                    window.location.href = "../index.html"
                }
                else {
                    alert(data.message);
                }
            }
        })
    }
})
