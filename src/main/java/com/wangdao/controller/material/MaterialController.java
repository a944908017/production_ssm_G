package com.wangdao.controller.material;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wangdao.bean.material.Material;
import com.wangdao.service.material.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;

/**
 * 物料信息controller实现层
 */
@Controller
public class MaterialController {

    /**
     * 自动注入业务层接口
     */
    @Autowired
    private MaterialService materialService;

    /**
     * 跳转到物料信息页面
     * @param modelAndView
     * @return
     */
    @RequestMapping("material/find")
    public ModelAndView findMaterial(ModelAndView modelAndView){

        modelAndView.setViewName("material_list");
        return modelAndView;
    }

    /**
     * 查询所有物料信息记录
     */
    @RequestMapping("/material/list")
    @ResponseBody
    public HashMap materialList(Integer page, Integer rows){

        PageHelper.startPage(page, rows);
        List<Material> materials  = materialService.selectAllMaterial();
        PageInfo<Material> pageInfo = new PageInfo<>(materials);
        long total = pageInfo.getTotal();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("total",total);
        hashMap.put("rows",materials);

        return hashMap;
    }


    /**
     * 跳转到material_add.jsp ，判断身份权限(未生效)
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = {"/material/add_judge", "/material/add"})
    public ModelAndView findAddMaterial(ModelAndView modelAndView){
        modelAndView.setViewName("material_add");
        return modelAndView;
    }

    /**
     * 增加物料信息
     * @return
     */
    @RequestMapping("/material/insert")
    @ResponseBody
    public HashMap inserMaterial(Material material){

        HashMap hashMap = materialService.insetMaterial(material);
        return hashMap;
    }

    /**
     * 跳转到物料信息编辑页面
     * @return
     */
    @RequestMapping( value = {"material/edit_judge","material/edit"})
    public ModelAndView findMaterialEdit(ModelAndView modelAndView){
        modelAndView.setViewName("material_edit");
        return modelAndView;
    }

    /**
     * 编辑更新物料信息
     */
    @RequestMapping("material/update_all")
    @ResponseBody
    public HashMap updateMaterial(Material material){

        HashMap hashMap = materialService.updateMaterial(material);
        return hashMap;
    }

    /**
     * 判断身份权限，管理员则跳转到删除物料信息操作
     */
    @RequestMapping("material/delete_judge")
    public ModelAndView findMaterialDelect(ModelAndView modelAndView){

       modelAndView.setViewName("material_list");
        return modelAndView;
    }

    /**
     * 删除物料信息
     */
    @RequestMapping("/material/delete_batch")
    @ResponseBody
    public HashMap deleteMaterial(String ids){

        HashMap hashMap = materialService.deleteMaterial(ids);
        return hashMap;
    }

    /**
     * 根据物料编号模糊搜索
     */
    @RequestMapping("material/search_material_by_materialId")
    @ResponseBody
    public HashMap searchMaterialByMaterialId(String searchValue,Integer page,Integer rows){
        return  materialService.searchMaterialByMaterialId(searchValue,page,rows);
    }

    /**
     * 根据物料类型模糊搜索
     */
    @RequestMapping("material/search_material_by_materialType")
    @ResponseBody
    public HashMap searchMaterialByMaterialType(Integer page,Integer rows,String searchValue){
        return materialService.searchMaterialByMaterialType(page,rows,searchValue);
    }

    /**
     * 修改备注
     */
    @RequestMapping("material/update_note")
    @ResponseBody
    public HashMap updateMaterialNote(Material material){
        return materialService.updateMaterialNote(material);
    }

}
