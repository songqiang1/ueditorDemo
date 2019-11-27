package com.opg.pc.business.controller.common;

import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.opg.pc.business.baidu.ueditor.ActionEnter;
import com.opg.pc.business.baidu.ueditor.ResponseUtils;
import com.opg.pc.business.config.FileConfig;
import com.opg.pc.business.service.FileService;
import db.entity.FileInfo;
import io.swagger.annotations.Api;
import com.beust.jcommander.internal.Lists;
import java.io.PrintWriter;
import net.sf.json.JSONObject;

@RestController
@Api(description = "ueditor图片上传接口")
@RequestMapping("/ueditor")
public class UEditorController{
    
    @Autowired
	private FileService fileService;
    /**
     * ueditor文件上传（上传到外部服务器）
     * @param request
     * @param response
     * @param action
     */
    @ResponseBody
    @RequestMapping(value="/ueditorUpload", method={RequestMethod.GET, RequestMethod.POST})
    public void editorUpload(HttpServletRequest request, HttpServletResponse response, String action) {
        String rootPath = request.getSession().getServletContext().getRealPath("/");
        try {
            if("config".equals(action)){    //如果是初始化
            	response.setContentType("text/javascript");
                String exec = new ActionEnter(request, rootPath).exec();
                PrintWriter writer = response.getWriter();
                writer.write(exec);
                writer.flush();
                writer.close();
            }else if("uploadimage".equals(action) || "uploadvideo".equals(action) || "uploadfile".equals(action)){    //如果是上传图片、视频、和其他文件
            	JSONObject obj = new JSONObject();
    			try {
    				for (MultipartFile file : getMultipartFileList(request)) {
    	
    					FileInfo fileInfo = fileService.uploadFile(file, null);
    	
    					obj.put("state", "SUCCESS");
    					obj.put("original", fileInfo.getFileOriginalName());
    					obj.put("size", file.getSize());
    					obj.put("title", fileInfo.getFileOriginalName());
    					obj.put("type", fileInfo.getFileType());
    					obj.put("url", fileInfo.getFilePath());
    					ResponseUtils.renderJson(response, obj.toString());
    				}
    			} catch (Exception e) {
    				e.printStackTrace();
    			}
    			
            }
        } catch (Exception e) {
        }
    }
    
    protected List<MultipartFile> getMultipartFileList(
			HttpServletRequest request) {
		List<MultipartFile> files = Lists.newArrayList();
		try {
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
					request.getSession().getServletContext());
			if (request instanceof MultipartHttpServletRequest) {
				// 将request变成多部分request
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
				Iterator<String> iter = multiRequest.getFileNames();
				// 检查form中是否有enctype="multipart/form-data"
				if (multipartResolver.isMultipart(request) && iter.hasNext()) {
					// 获取multiRequest 中所有的文件名
					while (iter.hasNext()) {
						// 适配名字重复的文件
						List<MultipartFile> fileRows = multiRequest
								.getFiles(iter.next().toString());
						if (fileRows != null && fileRows.size() != 0) {
							for (MultipartFile file : fileRows) {
								if (file != null && !file.isEmpty()) {
									files.add(file);
								}
							}
						}
					}
				}
			}
		} catch (Exception ex) {
			
		}
		return files;
	}
}
