package com.wangdao.controller.plan;

import com.wangdao.bean.dataPostman.FormDataPostman;
import com.wangdao.bean.dataPostman.StatusMessagePostman;
import com.wangdao.bean.plan.*;
import com.wangdao.service.plan.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Wangxy013
 * website：<a href="https://github.com/Wangxy013">GitHub:Wangxy013</a>
 * @version V1.0
 * @Description: ProductController:处理计划进度模块中产品管理的请求
 * @date 2018/9/1 2:01
 */
@Controller
@RequestMapping("/product")
public class ProductController {

    /**
     * 自动注入PlanService
     */
    @Autowired
    @Qualifier("PlanService")
    private PlanService planService;


    /*********产品管理**********/

    /**
     * 处理用户查询产品的请求，因为前端会先后发送两次请求，所以本方法的作用仅仅是转到静态页面，实际处理数据的工作由listProduct来实现
     *
     * @return
     */
    @RequestMapping(value = "/find")
    public String findProduct() {
        //转到产品列表显示的页面上
        return "product_list";
    }

    /**
     * 将数据库中的产品信息传到产品信息显示页面product_list.jsp
     *
     * @param page           请求的页数
     * @param rows           每页中的数据条数
     * @param productExample 订单对象
     * @return 返回一个FromDatePostman对象，其中包括该页面需要显示的数据
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public FormDataPostman listProduct(Integer page, Integer rows, ProductExample productExample) {

        FormDataPostman formDataPostman = new FormDataPostman();

        formDataPostman = planService.fingProduct(page, rows, productExample);

        //System.out.println("W//com.wangdao.controller.plan.ProductController.listOrder/formDataPostman="+ formDataPostman);

        return formDataPostman;

    }

    /**
     * 处理订单管理模块中客户信息框的回显请求
     *
     * @param productId 从前端随url发出的选中的订单对应的customId
     * @return 返回当前请求的订单对应的Custom对象
     * @throws Exception
     */
    @RequestMapping("/get/{productId}")
    @ResponseBody
    //@PathVariable用于将请求URL中的模板变量映射到功能处理方法的参数上
    public Product getProductByProductId(@PathVariable String productId) throws Exception {
        //System.out.println("w//com.wangdao.controller.plan.ProductController.getProductByProductId/productId="+productId);
        Product product = planService.getProductByProductId(productId);
        return product;
    }

    /**
     * 查询全部product对象用于添加order数据的页面的产品下拉框数据填充
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/get_data")
    @ResponseBody
    public List<Product> getProductDataBeforeOrderAdd() throws Exception {
        List<Product> productList = planService.findAllProductNotPage();
        return productList;
    }

    /**
     * Todo:删除字段之前先对用户的权限进行判断？
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/delete_judge")
    @ResponseBody
    private StatusMessagePostman delete_judge() throws Exception {
        return null;
    }

    /**
     * 删除前端传回的ids对应的数据
     *
     * @param ids 需要被删除的商品编号数组
     * @return 返回一个专门处理状态信息的对象
     * @throws Exception
     */
    @RequestMapping(value = "/delete_batch")
    @ResponseBody
    private StatusMessagePostman delete_batch(String[] ids) throws Exception {

        int result = planService.deleteProductsByIds(ids);
        if (result != 0) {
            return new StatusMessagePostman(200, "OK", null);
        } else {
            return null;
        }
    }

    /**
     * Todo:增加字段之前先对用户的权限进行判断？
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/add_judge")
    @ResponseBody
    private StatusMessagePostman add_judge() throws Exception {
        return null;
    }

    /**
     * 处理打开新增产品窗口的请求
     *
     * @return 转发到product_add.jsp
     * @throws Exception
     */
    @RequestMapping("/add")
    public String openProductADD() throws Exception {
        return "product_add";
    }

    /**
     * 实现新增订单的功能
     *
     * @param product
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    private StatusMessagePostman insert(Product product) throws Exception {
        //System.out.println("W//com.wangdao.controller.plan.COrderController.insert/product=" + product);

        StatusMessagePostman statusMessagePostman;
        if (planService.getProductByProductId(product.getProductId()) != null) {
            statusMessagePostman = new StatusMessagePostman(0, "该订单编号已经存在，请修改订单编号！", null);
        } else {
            int result = planService.insertProduct(product);
            if (result != 0) {
                statusMessagePostman = new StatusMessagePostman(200, "OK", null);
            } else {
                statusMessagePostman = null;
            }
        }
        return statusMessagePostman;
    }

    /**
     * 根据id模糊查询Product对象
     *
     * @param page
     * @param rows
     * @param searchValue
     * @return
     * @throws Exception
     */
    @RequestMapping("/search_product_by_productId")
    @ResponseBody
    public FormDataPostman searchProductByProductId(Integer page, Integer rows, String searchValue) throws Exception {
        ProductExample productExample = new ProductExample();
        ProductExample.Criteria criteria = productExample.createCriteria();
        criteria.andProductIdLike("%" + searchValue + "%");

        FormDataPostman result = listProduct(page, rows, productExample);
        return result;
    }

    /**
     * 根据productName模糊查询Product对象
     *
     * @param page
     * @param rows
     * @param searchValue
     * @return
     * @throws Exception
     */
    @RequestMapping("/search_product_by_productName")
    @ResponseBody
    public FormDataPostman searchProductByProductName(Integer page, Integer rows, String searchValue) throws Exception {
        ProductExample productExample = new ProductExample();
        ProductExample.Criteria criteria = productExample.createCriteria();
        criteria.andProductNameLike("%" + searchValue + "%");

        FormDataPostman result = listProduct(page, rows, productExample);
        return result;
    }

    /**
     * 根据productType模糊查询Product对象
     *
     * @param page
     * @param rows
     * @param searchValue
     * @return
     * @throws Exception
     */
    @RequestMapping("/search_product_by_productType")
    @ResponseBody
    public FormDataPostman searchProductByProductType(Integer page, Integer rows, String searchValue) throws Exception {
        ProductExample productExample = new ProductExample();
        ProductExample.Criteria criteria = productExample.createCriteria();
        criteria.andProductTypeLike("%" + searchValue + "%");

        FormDataPostman result = listProduct(page, rows, productExample);
        return result;
    }

    /**
     * Todo:推测是用于权限判断
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/edit_judge")
    @ResponseBody
    public String edit_judge() throws Exception {
        return null;
    }

    /**
     * 跳转到编辑页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/edit")
    public String openProductEdit() throws Exception {
        return "product_edit";
    }

    /**
     * 实现更新操作
     * /update_all 处理点击编辑按钮后弹窗中的编辑请求，处理点击关键字后弹窗中的编辑请求
     * /update_note 处理富文本信息编辑框中的修改请求
     *
     * @param product
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/update_all", "/update_note"})
    @ResponseBody
    public StatusMessagePostman updateProduct(@ModelAttribute Product product) throws Exception {
        //System.out.println("W//com.wangdao.controller.plan.ProductController.updateProduct/product=" + product);

        StatusMessagePostman statusMessagePostman;
        int result = planService.updateProduct(product);
        if (result != 0) {
            statusMessagePostman = new StatusMessagePostman(200, "OK", null);
        } else {
            statusMessagePostman = new StatusMessagePostman(100, "Error，更新失败！", "null");
        }

        return statusMessagePostman;
    }

}
