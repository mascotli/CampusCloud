package com.mascot.campuscloud.web;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mascot.campuscloud.dao.entity.LocalFolderDO;
import com.mascot.campuscloud.manager.constant.MenuConsts;
import com.mascot.campuscloud.service.DiskService;
import com.mascot.campuscloud.service.UserService;
import com.mascot.campuscloud.service.dto.LocalFileDTO;
import com.mascot.campuscloud.service.dto.LocalFolderDTO;
import com.mascot.campuscloud.service.dto.UserDTO;
import com.mascot.campuscloud.web.reqbody.MoveReqBody;
import com.mascot.campuscloud.web.reqbody.RenameFileReqBody;
import com.mascot.campuscloud.web.reqbody.RenameFolderReqBody;
import com.mascot.campuscloud.web.reqbody.ShredReqBody;

@RestController
@RequestMapping(value = "/api/v1", produces = "application/json", consumes = "application/json")
public class HomeController {

	@Autowired
	private UserService userService;
	@Autowired
	private DiskService diskService;

	/**
	 * 功能：根据用户名获取完整的用户信息<br />
	 * 示例：GET api/v1/users/admin，获取admin用户的信息
	 */
	@RequestMapping(value = "/users/{username}", method = RequestMethod.GET)
	public UserDTO getUserInfo(@PathVariable String username) {
		/* 参数已经经过拦截器校验，所以不需要再检查参数 */
		return userService.getUser(username);
	}

	/**
	 * 功能：获取用户不同菜单下的文件（及文件夹）列表<br />
	 * 示例：GET api/v1/users/admin/photo，获取admin用户的所有照片
	 */
	@RequestMapping(value = "/users/{username}/{menu}", method = RequestMethod.GET)
	public Map<String, Object> getMenu(@PathVariable String username, @PathVariable String menu) {
		/* 检查menu参数是否合法 */
		if (!MenuConsts.getConsts().contains(menu)) {
			throw new IllegalArgumentException("Illegal argument: " + menu);
		}

		long userID = userService.getUser(username).getId();
		return diskService.getMenuContents(userID, menu);
	}

	/**
	 * 功能：获取某个文件夹的内容<br />
	 * 示例：GET
	 * api/v1/users/admin/disk/folders/55?sort=1，获取admin用户网盘内folderID=55的文件夹内容，以最后修改时间排序
	 * 
	 * @param sort
	 *            =1按时间排序，sort=其它值 按中文拼音排序
	 */
	@RequestMapping(value = "/users/{username}/disk/folders/{folderID}", method = RequestMethod.GET)
	public Map<String, Object> getFolderContents(@PathVariable String username, @PathVariable Long folderID,
			@RequestParam(defaultValue = "0") int sort) {
		// TODO 参数校验
		long userID = userService.getUser(username).getId();
		return diskService.getFolderContents(userID, folderID, sort);
	}

	/**
	 * 功能：搜索用户网盘内的文件<br />
	 * 示例：GET api/v1/users/admin/disk/search?input=txt，搜索admin用户网盘内文件名含“txt”的文件及文件夹
	 */
	@RequestMapping(value = "/users/{username}/disk/search", method = RequestMethod.GET)
	public Map<String, Object> search(@PathVariable String username, @RequestParam String input) {
		long userID = userService.getUser(username).getId();
		Map<String, Object> result = diskService.search(userID, input);
		return result;
	}

	/**
	 * 功能：批量移动文件及文件夹<br />
	 * 示例：PATCH
	 * api/v1/users/admin/disk/move，请求体：{"files":[1,2],"folders":[3,4],"dest":5}，
	 * 将ID=1,2的文件及ID=3,4的文件夹移动到ID=5的文件夹中
	 */
	@RequestMapping(value = "/users/{username}/disk/move", method = RequestMethod.PATCH)
	public Map<String, Object> move(@PathVariable String username, @RequestBody @Valid MoveReqBody reqBody) {
		// long userID = userService.getUser(username).getId();
		// TODO 参数校验
		return diskService.move(reqBody.getFolders(), reqBody.getFiles(), reqBody.getDest());
	}

	/**
	 * 功能：重命名文件夹<br />
	 * 示例：PATCH api/v1/users/admin/disk/folders/123，请求体：{"localName":"rename_test"}，
	 * 把ID=123的文件夹重命名为rename_test
	 */
	@RequestMapping(value = "/users/{username}/disk/folders/{folderID}", method = RequestMethod.PATCH)
	public LocalFolderDTO renameFolder(@PathVariable String username, @PathVariable long folderID,
			@RequestBody @Valid RenameFolderReqBody reqBody) {
		// long userID = userService.getUser(username).getId();
		// TODO 参数校验
		return diskService.renameFolder(folderID, reqBody.getLocalName());
	}

	/**
	 * 功能：重命名文件<br />
	 * 示例：PATCH
	 * api/v1/users/admin/disk/files/123，请求体：{"localName":"rename","localType":"jpg"}，
	 * 把ID=123的文件重命名为rename.jpg
	 */
	@RequestMapping(value = "/users/{username}/disk/files/{fileID}", method = RequestMethod.PATCH)
	public LocalFileDTO renameFile(@PathVariable String username, @PathVariable long fileID,
			@RequestBody @Valid RenameFileReqBody reqBody) {
		// long userID = userService.getUser(username).getId();
		// TODO 参数校验
		return diskService.renameFile(fileID, reqBody.getLocalName(), reqBody.getLocalType());
	}

	/**
	 * 功能：新建文件夹<br/>
	 * 示例：POST api/v1/users/admin/disk/folders，请求体：{"localName":"新建文件夹","parent":5}，
	 * 在ID=5的文件夹下新建一个名字为“新建文件夹”的文件夹
	 */
	@RequestMapping(value = "/users/{username}/disk/folders", method = RequestMethod.POST)
	public LocalFolderDTO newFolder(@PathVariable String username, @RequestBody @Valid LocalFolderDO reqBody) {
		// TODO 参数校验
		long userID = userService.getUser(username).getId();
		reqBody.setUserID(userID);

		return diskService.newFolder(reqBody);
	}

	/**
	 * 功能：彻底删除回收站的文件<br />
	 * 示例：DELETE api/v1/users/admin/recycle，请求体：{"files":[11,22],"folders":[33,44]}，
	 * 删除ID=11,22的文件和ID=33,44的文件夹，并更新用户已用空间
	 */
	@RequestMapping(value = "/users/{username}/recycle", method = RequestMethod.DELETE)
	public UserDTO shred(@PathVariable String username, @RequestBody @Valid ShredReqBody reqBody) {
		long userID = userService.getUser(username).getId();
		// TODO 参数校验
		return diskService.shred(reqBody.getFolders(), reqBody.getFiles(), userID);
	}
}
