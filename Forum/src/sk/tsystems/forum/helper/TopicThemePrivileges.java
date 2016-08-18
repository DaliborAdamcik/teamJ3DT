package sk.tsystems.forum.helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;


import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import sk.tsystems.forum.entity.Comment;
import sk.tsystems.forum.entity.Theme;
import sk.tsystems.forum.entity.Topic;
import sk.tsystems.forum.entity.User;
import sk.tsystems.forum.entity.UserRole;
import sk.tsystems.forum.entity.common.BlockableEntity;
import sk.tsystems.forum.helper.exceptions.UnknownActionException;
import sk.tsystems.forum.helper.exceptions.WEBNoPermissionException;

public class TopicThemePrivileges {
	private ServletHelper svHelper;
	private URLParser pars;
	private HttpServletResponse response;
	private Class<?> clazz;
	
	/**
	 * @param svHelper
	 * @param pars
	 * @param response
	 * @param clazz
	 */
	public TopicThemePrivileges(ServletHelper svHelper, URLParser pars, HttpServletResponse response, Class<?> clazz) {
		super();
		if(!clazz.equals(Theme.class) && !clazz.equals(Comment.class) && !clazz.equals(Topic.class))
			throw new RuntimeException(getClass().getSimpleName()+" cant work with "+clazz.getSimpleName());
		
		this.svHelper = svHelper;
		this.pars = pars;
		this.response = response;
		this.clazz = clazz;
	}
	
	private void checkBlocked(List<BlockableEntity> toCheck) throws WEBNoPermissionException {
		if(svHelper.getSessionRole().equals(UserRole.ADMIN))
			return;
		
		for (BlockableEntity blo: toCheck) {
			if(blo==null || blo.isBlocked())  
				throw new WEBNoPermissionException(clazz.getSimpleName()+" not found / deleted.");
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getStoredObject(boolean checkOwnerShip) throws WEBNoPermissionException, UnknownActionException {
		if(svHelper.getSessionRole().equals(UserRole.GUEST))
			throw new WEBNoPermissionException("You must be signed in / regular user to add / edit "+clazz.getSimpleName().toLowerCase());

		User logged = svHelper.getLoggedUser(); 
		if(logged.isBlocked())
			throw new WEBNoPermissionException("You have no permissions to add "+clazz.getSimpleName().toLowerCase());

		if(pars.getParrentID()<0) 
			throw new UnknownActionException(clazz.getSimpleName()+" ID not specified.");
		
		Object obj = pars.getParentObject(clazz);

		if(obj == null)  
			throw new WEBNoPermissionException(clazz.getSimpleName()+" not found.");
		
		if(!obj.getClass().equals(clazz))
			throw new UnknownActionException("Unexpected object. Requested '"+clazz.getSimpleName()+
					"' bud received '"+obj.getClass().getSimpleName()+"'.");

		List<BlockableEntity> blockables = new ArrayList<>();
		
		blockables.add((BlockableEntity) obj);
		
		User owner = null;

		if(obj instanceof Comment)
		{
			blockables.add(((Comment) obj).getTheme());
			blockables.add(((Comment) obj).getTheme().getTopic());
			owner = ((Comment) obj).getOwner();
		}
		
		if(obj instanceof Theme)
		{
			blockables.add(((Theme) obj).getTopic());
			owner = ((Theme) obj).getAuthor();
		}
		
		checkBlocked(blockables);
		
		if(checkOwnerShip && !svHelper.getSessionRole().equals(UserRole.ADMIN) && logged.getId()!=owner.getId())  
			throw new WEBNoPermissionException("No permission to edit "+clazz.getSimpleName());
		
		return (T) obj;
	} 

	
	public void store(BlockableEntity obj) throws UnknownActionException, JsonGenerationException, JsonMappingException, IOException {

		if(obj instanceof Comment)
		{
			if(obj.getId()>0)
				svHelper.getCommentService().updateComment((Comment) obj);
			else
				svHelper.getCommentService().addComment((Comment) obj);
		}
		else
		if(obj instanceof Theme)
		{
			if(obj.getId()>0)
				svHelper.getThemeService().updateTheme((Theme) obj);
			else
				svHelper.getThemeService().addTheme((Theme) obj);
		}
		else
		if(obj instanceof Topic)
		{
			if(obj.getId()>0)
				svHelper.getTopicService().updateTopic((Topic) obj);
			else
				svHelper.getTopicService().addTopic((Topic) obj);
		}
		else
			throw new UnknownActionException("Cant strore object '"+obj.getClass().getSimpleName()+"'.");
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		Map<String, Object> resp = new HashMap<>();
		resp.put(obj.getClass().getSimpleName().toLowerCase(), obj);
		response.setContentType("application/json");
		mapper.writeValue(response.getWriter(), resp);
	}
}
