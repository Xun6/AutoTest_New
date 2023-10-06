// 自定义模块
layui.define(function(exports){
    var obj = {
        hello: function(str){
            alert('Hello '+ (str||'mymod'));
        },
        say: function(str){
            alert('say '+ (str||'mymod'));
        }
    };
    exports('mymod',obj);
});