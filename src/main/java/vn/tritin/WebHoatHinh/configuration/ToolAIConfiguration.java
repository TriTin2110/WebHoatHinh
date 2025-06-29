package vn.tritin.WebHoatHinh.configuration;

import java.lang.reflect.Method;
import java.util.List;

import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.definition.ToolDefinition;
import org.springframework.ai.tool.method.MethodToolCallback;
import org.springframework.ai.util.json.schema.JsonSchemaGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;

import vn.tritin.WebHoatHinh.tool.VideoTool;

@Configuration
public class ToolAIConfiguration {
	@Autowired
	private VideoTool videoTool;
	private final String DESCRIPTION_FOR_GET_VIDEO = "Tìm các video có id nằm trong danh sách cho trước";

	@Bean
	public List<ToolCallback> getToolVideo() {
		return List.of(getVideoToolCallback());
	}

	public ToolCallback getVideoToolCallback() {
		Method method = ReflectionUtils.findMethod(VideoTool.class, "getVideo", List.class);
		ToolCallback toolCallback = MethodToolCallback.builder()
				.toolDefinition(ToolDefinition.builder().name(method.getName()).description(DESCRIPTION_FOR_GET_VIDEO)
						.inputSchema(JsonSchemaGenerator.generateForMethodInput(method)).build())
				.toolMethod(method).toolObject(videoTool).build();
		return toolCallback;
	}
}
