(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-f1a07780","chunk-2d217eda"],{"1f27":function(e,t,n){"use strict";n.d(t,"c",function(){return o}),n.d(t,"d",function(){return a}),n.d(t,"a",function(){return l}),n.d(t,"b",function(){return c});var r=n("b775");function o(e){return Object(r["a"])({url:"/tesseract-menu/allMenu",method:"get",params:e})}function a(e){return Object(r["a"])({url:"/tesseract-menu/menuList",method:"get",params:e})}function l(e){return Object(r["a"])({url:"/tesseract-menu/saveOrUpdateMenu",method:"post",data:e})}function c(e){return Object(r["a"])({url:"/tesseract-menu/deleteMenu",method:"get",params:e})}},2937:function(e,t,n){},"740b":function(e,t,n){"use strict";n.d(t,"c",function(){return o}),n.d(t,"d",function(){return a}),n.d(t,"a",function(){return l}),n.d(t,"b",function(){return c});var r=n("b775");function o(e){return Object(r["a"])({url:"/tesseract-executor/executorList",method:"get",params:e})}function a(e){return Object(r["a"])({url:"/tesseract-executor/executorListNoDetail",method:"get",params:e})}function l(e){return Object(r["a"])({url:"/tesseract-executor/addExecutor",method:"post",data:e})}function c(e){return Object(r["a"])({url:"/tesseract-executor/deleteExecutor",method:"get",params:e})}},"97a3":function(e,t,n){"use strict";n.r(t);var r=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"app-container"},[n("el-row",[n("el-form",{attrs:{inline:!0,model:e.selectInfo}},[n("el-form-item",{attrs:{label:"角色名"}},[n("el-input",{attrs:{placeholder:"角色名"},model:{value:e.selectInfo.roleName,callback:function(t){e.$set(e.selectInfo,"roleName",t)},expression:"selectInfo.roleName"}})],1),e._v(" "),n("el-form-item",[n("el-button",{attrs:{type:"primary"},on:{click:e.getRoleList}},[e._v("查询")])],1),e._v(" "),n("el-form-item",[n("el-button",{attrs:{type:"success"},on:{click:function(t){return e.addRoleInfo(null)}}},[e._v("新增角色")])],1)],1)],1),e._v(" "),n("el-row",[n("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.listLoading,expression:"listLoading"}],staticStyle:{width:"100%"},attrs:{data:e.roleList,border:"",fit:"","highlight-current-row":""}},[n("el-table-column",{attrs:{align:"center",label:"ID",width:"80"},scopedSlots:e._u([{key:"default",fn:function(t){return[n("span",[e._v(e._s(t.row.id))])]}}])}),e._v(" "),n("el-table-column",{attrs:{align:"center",label:"角色名"},scopedSlots:e._u([{key:"default",fn:function(t){return[n("span",[e._v(e._s(t.row.roleName))])]}}])}),e._v(" "),n("el-table-column",{attrs:{align:"center",label:"角色描述"},scopedSlots:e._u([{key:"default",fn:function(t){return[n("span",[e._v(e._s(t.row.roleDesc))])]}}])}),e._v(" "),n("el-table-column",{attrs:{align:"center",label:"创建人"},scopedSlots:e._u([{key:"default",fn:function(t){return[n("span",[e._v(e._s(t.row.createUserName))])]}}])}),e._v(" "),n("el-table-column",{attrs:{align:"center",label:"更新人"},scopedSlots:e._u([{key:"default",fn:function(t){return[n("span",[e._v(e._s(t.row.updateUserName))])]}}])}),e._v(" "),n("el-table-column",{attrs:{align:"center",label:"创建时间",width:"180"},scopedSlots:e._u([{key:"default",fn:function(t){return[n("span",[e._v(e._s(t.row.createTime&&0!=t.row.createTime?e.parseTime(t.row.createTime):""))])]}}])}),e._v(" "),n("el-table-column",{attrs:{align:"center",label:"操作",width:"440"},scopedSlots:e._u([{key:"default",fn:function(t){return[n("el-button",{attrs:{type:"warning",size:"small",icon:"el-icon-edit"},on:{click:function(n){return e.addRoleInfo(t.row)}}},[e._v("\n            修改\n          ")]),e._v(" "),n("el-button",{attrs:{type:"danger",size:"small",icon:"el-icon-delete"},on:{click:function(n){return e.deleteRole(t.row)}}},[e._v("\n            删除\n          ")])]}}])})],1)],1),e._v(" "),n("el-row",[n("el-pagination",{attrs:{total:e.selectInfo.total,"current-page":e.selectInfo.currentPage,"page-size":e.selectInfo.pageSize,align:"center",background:"",layout:"prev, pager, next"},on:{"current-change":e.pageChange}})],1),e._v(" "),n("el-dialog",{directives:[{name:"el-drag-dialog",rawName:"v-el-drag-dialog"}],attrs:{visible:e.dialogTableVisible,title:"角色信息"},on:{"update:visible":function(t){e.dialogTableVisible=t},dragDialog:e.handleDrag}},[n("el-form",{ref:"roleForm",attrs:{inline:!1,model:e.roleInfo,rules:e.roleRules,"label-width":"120px"}},[n("el-form-item",{attrs:{label:"角色名",props:"roleName"}},[n("el-input",{ref:"name",attrs:{placeholder:"角色名"},model:{value:e.roleInfo.roleName,callback:function(t){e.$set(e.roleInfo,"roleName",t)},expression:"roleInfo.roleName"}})],1),e._v(" "),n("el-form-item",{attrs:{label:"菜单"}},[n("el-col",{attrs:{span:12}},[n("el-tree",{ref:"tree",attrs:{"expand-on-click-node":!1,"check-strictly":!0,"check-on-click-node":!0,data:e.menuTreeData,"show-checkbox":"","highlight-current":!0,"node-key":"id","default-expanded-keys":e.expandedKeyList,"default-checked-keys":e.checkedKeyList,props:e.defaultProps},on:{check:e.nodeCheck,"node-click":e.nodeClick}})],1),e._v(" "),n("el-col",{attrs:{span:12}},[n("el-checkbox-group",{style:e.styleObject,model:{value:e.btnCheckList,callback:function(t){e.btnCheckList=t},expression:"btnCheckList"}},e._l(e.btnList,function(e){return n("el-checkbox",{attrs:{label:e.btnName}})}),1)],1)],1),e._v(" "),n("el-form-item",{attrs:{label:"角色描述",props:"roleDesc"}},[n("el-input",{ref:"roleDesc",attrs:{placeholder:"角色描述"},model:{value:e.roleInfo.roleDesc,callback:function(t){e.$set(e.roleInfo,"roleDesc",t)},expression:"roleInfo.roleDesc"}})],1),e._v(" "),n("el-form-item",[n("el-button",{attrs:{type:"primary"},on:{click:e.saveRole}},[e._v("保存")])],1)],1)],1)],1)},o=[],a=(n("5df3"),n("f400"),n("ac4d"),n("8a81"),n("ac6a"),n("a888")),l=n("cc5e"),c=(n("740b"),n("ed08")),s=(n("c98f"),n("2a40")),i=n("1f27"),u=n("a15b"),d={name:"Role",directives:{elDragDialog:a["a"]},data:function(){var e={defaultProps:{children:"children",label:"label"},menuDataMap:null,menuTreeData:[],expandedKeyList:[],checkedKeyList:[],roleRules:{roleName:[{required:!0,message:"请输入角色名",trigger:"blur"}],roleDesc:[{required:!0,message:"请输入角色描述",trigger:"blur"}]},roleList:[],btnCheckList:[],btnList:[],btnMap:null,selectInfo:{currentPage:1,pageSize:10,total:0,status:null},styleObject:{display:"none"},dialogTableVisible:!1,roleInfo:{roleName:null,roleDesc:null,id:null},listLoading:!1,prevNode:null,menuBtnMap:null};return e},mounted:function(){this.getRoleList()},methods:{nodeCheck:function(e,t){},nodeClick:function(e,t){var n=this;this.prevNode&&this.menuBtnMap.set(this.prevNode.id,this.btnCheckList);var r=this.menuBtnMap.get(e.id);r?(this.btnCheckList=r,this.prevNode=e,this.btnVisible="block"):Object(u["c"])({menuId:e.id,roleId:this.roleInfo.roleId}).then(function(t){n.btnCheckList=[];var r=!0,o=!1,a=void 0;try{for(var l,c=t[Symbol.iterator]();!(r=(l=c.next()).done);r=!0){var s=l.value;n.btnCheckList.push(s.btnName)}}catch(i){o=!0,a=i}finally{try{r||null==c.return||c.return()}finally{if(o)throw a}}n.menuBtnMap.set(e.id,n.btnCheckList),n.prevNode=e,n.styleObject.display="block"})},pageChange:function(e){this.selectInfo.currentPage=e,this.getRoleList()},parseTime:c["d"],getRoleList:function(){var e=this;Object(l["f"])(this.selectInfo).then(function(t){e.selectInfo.currentPage=t.pageInfo.currentPage,e.selectInfo.pageSize=t.pageInfo.pageSize,e.selectInfo.total=t.pageInfo.total,e.roleList=t.roleList})},handleDrag:function(){this.$refs.select.blur()},addRoleInfo:function(e){var t=this;this.styleObject.display="none",this.menuBtnMap=new Map,this.prevNode=null,this.btnCheckList=[],this.checkedKeyList=[];var n=this;s["a"].clearObject(this.roleInfo),Promise.all([Object(i["c"])(),Object(u["e"])()]).then(function(r){var o=s["a"].listToTreeData(r[0]),a=o.treeDataMap,c=o.treeList;n.menuDataMap=a,t.menuTreeData=c,n.btnList=r[1],n.btnMap=s["a"].listToMap(r[1],"btnName","id"),e?Object(l["e"])({roleId:e.id}).then(function(r){n.checkedKeyList=r,n.expandedKeyList=r,e&&(t.roleInfo.roleId=e.id,t.roleInfo.roleName=e.roleName,t.roleInfo.roleDesc=e.roleDesc),t.dialogTableVisible=!0}):t.dialogTableVisible=!0})},saveRole:function(){var e=this;this.$refs.roleForm.validate(function(t){if(!t)return e.$message.error("表单填写错误"),!1;e.prevNode&&e.menuBtnMap.set(e.prevNode.id,e.btnCheckList);var n=[],r=e.$refs.tree.getCheckedNodes(),o=!0,a=!1,c=void 0;try{for(var s,i=r[Symbol.iterator]();!(o=(s=i.next()).done);o=!0){var u=s.value,d=e.menuBtnMap.get(u.id),f=null;if(d){f=[];var m=!0,p=!1,b=void 0;try{for(var h,g=d[Symbol.iterator]();!(m=(h=g.next()).done);m=!0){var v=h.value;f.push({id:e.btnMap.get(v),name:v})}}catch(y){p=!0,b=y}finally{try{m||null==g.return||g.return()}finally{if(p)throw b}}}n.push({menuId:u.id,menuName:u.label,btnList:f})}}catch(y){a=!0,c=y}finally{try{o||null==i.return||i.return()}finally{if(a)throw c}}e.roleInfo.menuInfo=n,Object(l["a"])(e.roleInfo).then(function(){e.$message({message:"保存成功",type:"success"}),e.getRoleList(),e.dialogTableVisible=!1})})},deleteRole:function(e){var t=this;this.$confirm("此操作将永久删除, 是否继续?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(function(){Object(l["b"])({roleId:e.id}).then(function(){t.$message({message:"删除成功",type:"success"}),t.getRoleList()})})}}},f=d,m=(n("ce5b"),n("2877")),p=Object(m["a"])(f,r,o,!1,null,"21c8de1c",null);t["default"]=p.exports},a15b:function(e,t,n){"use strict";n.d(t,"e",function(){return o}),n.d(t,"b",function(){return a}),n.d(t,"c",function(){return l}),n.d(t,"a",function(){return c}),n.d(t,"d",function(){return s});var r=n("b775");function o(e){return Object(r["a"])({url:"/tesseract-btn-resource/allBtn",method:"get",params:e})}function a(e){return Object(r["a"])({url:"/tesseract-btn-resource/btnList",method:"get",params:e})}function l(e){return Object(r["a"])({url:"/tesseract-btn-resource/btnListByMenuIdAndRoleId",method:"get",params:e})}function c(e){return Object(r["a"])({url:"/tesseract-btn-resource/saveOrUpdateBtn",method:"post",data:e})}function s(e){return Object(r["a"])({url:"/tesseract-btn-resource/deleteBtn",method:"get",params:e})}},a888:function(e,t,n){"use strict";n("a481"),n("6762"),n("2fdb");var r={bind:function(e,t,n){var r=e.querySelector(".el-dialog__header"),o=e.querySelector(".el-dialog");r.style.cssText+=";cursor:move;",o.style.cssText+=";top:0px;";var a=function(){return window.document.currentStyle?function(e,t){return e.currentStyle[t]}:function(e,t){return getComputedStyle(e,!1)[t]}}();r.onmousedown=function(e){var t=e.clientX-r.offsetLeft,l=e.clientY-r.offsetTop,c=o.offsetWidth,s=o.offsetHeight,i=document.body.clientWidth,u=document.body.clientHeight,d=o.offsetLeft,f=i-o.offsetLeft-c,m=o.offsetTop,p=u-o.offsetTop-s,b=a(o,"left"),h=a(o,"top");b.includes("%")?(b=+document.body.clientWidth*(+b.replace(/\%/g,"")/100),h=+document.body.clientHeight*(+h.replace(/\%/g,"")/100)):(b=+b.replace(/\px/g,""),h=+h.replace(/\px/g,"")),document.onmousemove=function(e){var r=e.clientX-t,a=e.clientY-l;-r>d?r=-d:r>f&&(r=f),-a>m?a=-m:a>p&&(a=p),o.style.cssText+=";left:".concat(r+b,"px;top:").concat(a+h,"px;"),n.child.$emit("dragDialog")},document.onmouseup=function(e){document.onmousemove=null,document.onmouseup=null}}}},o=function(e){e.directive("el-drag-dialog",r)};window.Vue&&(window["el-drag-dialog"]=r,Vue.use(o)),r.install=o;t["a"]=r},c98f:function(e,t,n){"use strict";n.r(t);var r=n("2a40"),o=[{key:0,value:"hash随机"},{key:2,value:"负载均衡"}],a=[{key:null,value:"全部"},{key:0,value:"停止"},{key:1,value:"已启动"}];t["default"]={strategyList:o,statusList:a,strategyMap:r["a"].listToMap(o),statusMap:r["a"].listToMap(a)}},cc5e:function(e,t,n){"use strict";n.d(t,"e",function(){return o}),n.d(t,"d",function(){return a}),n.d(t,"c",function(){return l}),n.d(t,"f",function(){return c}),n.d(t,"a",function(){return s}),n.d(t,"b",function(){return i});var r=n("b775");function o(e){return Object(r["a"])({url:"/tesseract-role/getRoleMenu",method:"get",params:e})}function a(e){return Object(r["a"])({url:"/tesseract-role/getRoleByUserId",method:"get",params:e})}function l(e){return Object(r["a"])({url:"/tesseract-role/allRole",method:"get",params:e})}function c(e){return Object(r["a"])({url:"/tesseract-role/roleList",method:"get",params:e})}function s(e){return Object(r["a"])({url:"/tesseract-role/saveOrUpdateRole",method:"post",data:e})}function i(e){return Object(r["a"])({url:"/tesseract-role/deleteRole",method:"get",params:e})}},ce5b:function(e,t,n){"use strict";var r=n("2937"),o=n.n(r);o.a}}]);