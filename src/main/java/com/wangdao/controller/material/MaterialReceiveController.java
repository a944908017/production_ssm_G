package com.wangdao.controller.material;


import com.wangdao.bean.material.Material;
import com.wangdao.bean.material.MaterialReceive;
import com.wangdao.service.material.MaterialReceiveService;
import com.wangdao.service.material.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;

/**
 * 物料收入controller层
 */
@Controller
public class MaterialReceiveController {

    //自动注入service对象
    @Autowired
    private MaterialReceiveService materialReceiveService;

    @Autowired
    private MaterialService materialService;

    /**
     * 跳转物料收入信息页面
     * @param modelAndView
     * @return
     */
    @RequestMapping("materialReceive/find")
    public ModelAndView findMaterialReceive(ModelAndView modelAndView){
        modelAndView.setViewName("materialReceive_list");
        return modelAndView;
    }

    /**
     * 显示所有物料收入信息
     */
    @RequestMapping("materialReceive/list")
    @ResponseBody
    public HashMap materialReceiveList(Integer page,Integer rows){

        HashMap hashMap = materialReceiveService.selectMaterialReceiveList(page,rows);
        return hashMap;
    }

    /**
     * 打开物料信息框
     */
    @RequestMapping("material/get/{materialId}")
    @ResponseBody
    public Material findMaterial(Material material){

        String materialId = material.getMaterialId();
        Material material1 = materialService.selectMaterialByMaterialId(materialId);
        return material1;
    }

    /**
     * 判断用户权限，是否可以执行新增操作
     */
    @RequestMapping("materialReceive/add_judge")
    @ResponseBody
    public String addJundge(){
        return null;
    }

    /**
     * 跳转到add.jsp
     * @return
     */
    @RequestMapping("/materialReceive/add")
    public ModelAndView findAddMaterialReceive(ModelAndView modelAndView){
        modelAndView.setViewName("materialReceive_add");
        return modelAndView;
    }

    /**
     * 物料收入中物料信息下拉框
     */
    @RequestMapping("material/get_data")
    @ResponseBody
    public List<Material> getMaterialData(){
        List<Material> materialList = materialService.selectAllMaterial();
        return materialList;
    }

    /**
     * 执行新增操作
     */
    @RequestMapping("materialReceive/insert")
    @ResponseBody
     public HashMap insertMaterialReceive(MaterialReceive materialReceive, String materialReceiveParams){

         return materialReceiveService.insertMaterialReceive(materialReceive);

    }

    /**
     * 编辑操作判断权限
     */
    @RequestMapping("materialReceive/edit_judge")
    @ResponseBody
    public String editJudge(){
        return null;
    }

    /**
     *跳转编辑页面
     */
    @RequestMapping("materialReceive/edit")
    public ModelAndView findMaterialReceiveEdit(ModelAndView modelAndView){
        modelAndView.setViewName("materialReceive_edit");
        return modelAndView;
    }

    /**
     * 编辑物料收入
     */
    @RequestMapping("materialReceive/update_all")
    @ResponseBody
    public HashMap updateMaterialReceive(MaterialReceive materialReceive){
        HashMap hashMap = materialReceiveService.updateMaterialReceive(materialReceive);
        return hashMap;
    }

    /**
     * 删除权限判断
     */
    @RequestMapping("materialReceive/delete_judge")
    @ResponseBody
    public String deleteJuage(){
        return null;
    }

    /**
     * 删除物料收入
     */
    @RequestMapping("materialReceive/delete_batch")
    @ResponseBody
    public HashMap deleteMaterialReceive(String ids){
        HashMap hashMap = materialReceiveService.deleteMaterialReceive(ids);
        return hashMap;
    }

    /**
     * 根据物料收入编号模糊搜索
     */
    @RequestMapping("materialReceive/search_materialReceive_by_receiveId")
    @ResponseBody
    public HashMap searchMaterialReceiveByReceiveId(String searchValue,Integer page,Integer rows){
        return materialReceiveService.searchMaterialReceiveByReceiveId(searchValue,page,rows);
    }

    /**
     * 根据物料收入类型模糊搜索materialId
     */
    @RequestMapping("materialReceive/search_materialReceive_by_materialId")
    @ResponseBody
    public HashMap searchMaterialReceiveByType(String searchValue,Integer page,Integer rows){
        return materialReceiveService.searchMaterialReceiveByMaterialId(searchValue,page,rows);
    }

    /**
     * 编辑备注
     */
    @RequestMapping("materialReceive/update_note")
    @ResponseBody
    public HashMap updateMaterialReceiveNote(MaterialReceive materialReceive){
        return materialReceiveService.updateMaterialReceiveNote(materialReceive);
    }
}
