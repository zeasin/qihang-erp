<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="订单号" prop="orderNum">
        <el-input
          v-model="queryParams.orderNum"
          placeholder="请输入订单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="店铺" prop="shopId">
        <el-select v-model="form.shopId" filterable r placeholder="搜索店铺" >
          <el-option v-for="item in shopList" :key="item.id" :label="item.name" :value="item.id">
            <span style="float: left">{{ item.name }}</span>
            <span style="float: right; color: #8492a6; font-size: 13px"  v-if="item.type === 1">1688</span>
            <span style="float: right; color: #8492a6; font-size: 13px"  v-if="item.type === 2">视频号小店</span>
            <span style="float: right; color: #8492a6; font-size: 13px"  v-if="item.type === 3">京东</span>
            <span style="float: right; color: #8492a6; font-size: 13px"  v-if="item.type === 4">淘系店铺</span>
            <span style="float: right; color: #8492a6; font-size: 13px"  v-if="item.type === 5">拼多多</span>
            <span style="float: right; color: #8492a6; font-size: 13px"  v-if="item.type === 6">抖店</span>
            <span style="float: right; color: #8492a6; font-size: 13px"  v-if="item.type === 7">小红书</span>
            <span style="float: right; color: #8492a6; font-size: 13px"  v-if="item.type === 8">快手小店</span>
            <span style="float: right; color: #8492a6; font-size: 13px"  v-if="item.type === 99">其他</span>
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="供应商" prop="supplierId">
        <el-select
          v-model="queryParams.supplierId"
          filterable
          placeholder="请选择供应商">
          <el-option
            v-for="item in supplierList"
            :key="item.id"
            :label="item.name"
            :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
        >结算</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['fms:agentShip:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="agentShipList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
<!--      <el-table-column label="${comment}" align="center" prop="id" />-->
      <el-table-column label="订单号" align="center" prop="orderNum" />
      <el-table-column label="店铺" align="center" prop="shopId" >
        <template slot-scope="scope">
          <span>{{ shopList.find(x=>x.id === scope.row.shopId)?shopList.find(x=>x.id === scope.row.shopId).name :'' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="供应商" align="center" prop="supplierId" >
        <template slot-scope="scope">
          <span>{{ supplierList.find(x=>x.id === scope.row.supplierId)?supplierList.find(x=>x.id === scope.row.supplierId).name :'' }}</span>
        </template>
      </el-table-column>
<!--      <el-table-column label="供应商" align="center" prop="supplierName" />-->
      <el-table-column label="账单日期" align="center" prop="date" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.date, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="物流公司" align="center" prop="logisticsCompany" />
      <el-table-column label="物流单号" align="center" prop="logisticsCode" />
      <el-table-column label="商品金额" align="center" prop="goodsAmount" />
      <el-table-column label="物流费用" align="center" prop="shipAmount" />
      <el-table-column label="总金额" align="center" prop="totalAmount" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="状态" align="center" prop="status" >
        <template slot-scope="scope">
          <el-tag type="info" v-if="scope.row.status === 0">未结算</el-tag>
          <el-tag type="success" v-if="scope.row.status === 1">已结算</el-tag>
        </template>
      </el-table-column>
<!--      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">-->
<!--        <template slot-scope="scope">-->
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-edit"-->
<!--            @click="handleUpdate(scope.row)"-->
<!--            v-hasPermi="['fms:agentShip:edit']"-->
<!--          >付款</el-button>-->
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-delete"-->
<!--            @click="handleDelete(scope.row)"-->
<!--            v-hasPermi="['fms:agentShip:remove']"-->
<!--          >删除</el-button>-->
<!--        </template>-->
<!--      </el-table-column>-->
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />


  </div>
</template>

<script>
import { listAgentShip, getAgentShip, delAgentShip, addAgentShip, updateAgentShip } from "@/api/fms/agentShip";
import {listShop} from "@/api/shop/shop";
import {listSupplier} from "@/api/scm/supplier";

export default {
  name: "agentShipFee",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 财务管理-应付款-代发账单表格数据
      agentShipList: [],
      shopList:[],
      supplierList:[],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderNum: null,
        shopId: null,
        supplierId: null,
        supplierName: null,
        date: null,
        shipCompany: null,
        shipNo: null,
        amount: null,
        shipAmount: null,
        goodsAmount: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        orderNum: [
          { required: true, message: "订单号不能为空", trigger: "blur" }
        ],
        shopId: [
          { required: true, message: "店铺id不能为空", trigger: "blur" }
        ],
        supplierId: [
          { required: true, message: "供应商id不能为空", trigger: "blur" }
        ],
        date: [
          { required: true, message: "日期不能为空", trigger: "blur" }
        ],
        shipCompany: [
          { required: true, message: "物流公司不能为空", trigger: "blur" }
        ],
        shipNo: [
          { required: true, message: "物流单号不能为空", trigger: "blur" }
        ],
        amount: [
          { required: true, message: "应付总金额不能为空", trigger: "blur" }
        ],
        shipAmount: [
          { required: true, message: "物流费用不能为空", trigger: "blur" }
        ],
        goodsAmount: [
          { required: true, message: "商品金额不能为空", trigger: "blur" }
        ],
        status: [
          { required: true, message: "状态不能为空", trigger: "change" }
        ],
      }
    };
  },
  created() {
    listShop({type:this.queryParams.shopType}).then(response => {
      this.shopList = response.rows;
    });
    listSupplier({}).then(response => {
      this.supplierList = response.rows;
    });
    this.getList();
  },
  methods: {
    /** 查询财务管理-应付款-代发账单列表 */
    getList() {
      this.loading = true;
      listAgentShip(this.queryParams).then(response => {
        this.agentShipList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        orderNum: null,
        shopId: null,
        supplierId: null,
        supplierName: null,
        date: null,
        shipCompany: null,
        shipNo: null,
        amount: null,
        shipAmount: null,
        goodsAmount: null,
        remark: null,
        status: null,
        createTime: null,
        createBy: null,
        updateTime: null,
        updateBy: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加财务管理-应付款-代发账单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getAgentShip(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改财务管理-应付款-代发账单";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {

        }
      });
    },

    /** 导出按钮操作 */
    handleExport() {
      this.download('fms/agentShip/export', {
        ...this.queryParams
      }, `agentShip_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
