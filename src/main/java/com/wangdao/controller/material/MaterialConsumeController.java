package com.wangdao.controller.material;

import com.wangdao.bean.material.Material;
import com.wangdao.bean.material.MaterialConsume;
import com.wangdao.bean.plan.Work;
import com.wangdao.service.material.MaterialConsumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;

@Controller
public class MaterialConsumeController {

    /**
     * 自动注入service层
     */
    @Autowired
    private MaterialConsumeService consumeService;


    /**
     * 跳转物料消耗页面
     */
    @RequestMapping("materialConsume/find")
    public ModelAndView findMaterialConsume(ModelAndView modelAndView){
        modelAndView.setViewName("materialConsume_list");
        return modelAndView;
    }

    /**
     * 查询所有物料消耗记录
     */
   @RequestMapping("materialConsume/list")
   @ResponseBody
    public HashMap SelectMaterialConsumeList(Integer page,Integer rows){

       HashMap hashMap = consumeService.selectMaterialConsumeList(page,rows);
       return hashMap;
   }

    /**
     * 增加操作判断用户权限
     */
    @RequestMapping("materialConsume/add_judge")
    @ResponseBody
    public String addJudge(){
        return null;
    }

    /**
     * 打开add.jsp
     */
    @RequestMapping("materialConsume/add")
    public ModelAndView findAddMaterialConsume(ModelAndView modelAndView){
        modelAndView.setViewName("materialConsume_add");
        return modelAndView;
    }


    /**
     * 新增物料消耗
     */
    @RequestMapping("materialConsume/insert")
    @ResponseBody
    public HashMap insertMaterialConsume(MaterialConsume materialConsume){
        HashMap<String, Object> hashMap = new HashMap<>();

         hashMap = consumeService.insertMaterialConsume(materialConsume);
        return hashMap;
    }

    /**
     * 判断编辑操作权限
     */
    @RequestMapping("materialConsume/edit_judge")
    @ResponseBody
    public String editJudge(){
        return null;
    }

    /**
     *跳转到编辑页面
     */
    @RequestMapping("materialConsume/edit")
    public ModelAndView findMaterialConsumeEdit(ModelAndView modelAndView){
        modelAndView.setViewName("materialConsume_edit");
        return modelAndView;
    }

    /**
     * 更新物料消耗信息
     */
    @RequestMapping("materialConsume/update_all")
    @ResponseBody
    public HashMap updateMateriaoConsume(MaterialConsume materialConsume){
        return consumeService.updateMaterialConsume(materialConsume);
    }

    /**
     * 判断删除操作权限
     */
    @RequestMapping("materialConsume/delete_judge")
    @ResponseBody
    public String deleteJudge(){
        return null;
    }

    /**
     * 删除物料消耗
     */
    @RequestMapping("materialConsume/delete_batch")
    @ResponseBody
    public HashMap deleteMaterialConsume(String ids){
        HashMap hashMap = consumeService.deleteMaterialConsume(ids);
        return hashMap;
    }

    /**
     * 根据物料消耗编号模糊搜索
     */
    @RequestMapping("materialConsume/search_materialConsume_by_consumeId")
    @ResponseBody
    public HashMap searchMaterialConsumeByConsumeId(String searchValue,Integer page,Integer rows){
        return consumeService.searchMaterialConsumeByConsumeId(searchValue,page,rows);
    }

    /**
     * 根据作业编号模糊搜索
     */
    @RequestMapping("materialConsume/search_materialConsume_by_workId")
    @ResponseBody
    public HashMap searchMaterialConsumeByWorkId(String searchValue,Integer page,Integer rows){
        return  consumeService.searchMaterialConsumeByWorkId(searchValue,page,rows);
    }

    /**
     * 根据物料编号模糊搜索
     */
    @RequestMapping("materialConsume/search_materialConsume_by_materialId")
    @ResponseBody
    public HashMap searchMaterialConsumeByMaterialId(String searchValue,Integer page,Integer rows){
        return consumeService.searchMaterialConsumeByMaterialId(searchValue,page,rows);
    }

    /**
     * 更新备注
     */
    @RequestMapping("materialConsume/update_note")
    @ResponseBody
    public HashMap updateMaterialConsumeNote(MaterialConsume materialConsume){
        return consumeService.updateMaterialConsumeNote(materialConsume);
    }
}
