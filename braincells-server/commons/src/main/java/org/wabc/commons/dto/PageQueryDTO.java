package org.wabc.commons.dto;


import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Data
@Schema(description = "分页查询基础参数-支持多排序字段")
public class PageQueryDTO {

    @Schema(description = "页码（从1开始）", example = "1")
    @Min(value = 1, message = "页码必须大于0")
    private Integer pageNum = 1;

    @Schema(description = "每页数量", example = "10")
    @Min(value = 1, message = "每页条数必须大于0")
    private Integer pageSize = 10;

    @Valid
    @Schema(
            description = "排序字段列表（支持多字段排序，按照数组顺序优先级排序）",
            example = "[{\"field\":\"createTime\",\"order\":\"desc\"}, {\"field\":\"name\",\"order\":\"asc\"}]"
    )
    private List<SortItem> sortFields;

    @Data
    @Schema(description = "排序字段及方式")
    public static class SortItem {
        @Schema(description = "排序字段名", example = "createTime")
        private String field;

        @Schema(description = "排序方式", example = "desc", allowableValues = {"asc", "desc"})
        private String order;
    }

    /**
     * 转换为MyBatis-Plus的Page对象
     */
    public <T> Page<T> toPage() {
        Page<T> page = new Page<>(pageNum, pageSize);
        if (sortFields != null && !sortFields.isEmpty()) {
            for (SortItem item : sortFields) {
                // 安全处理：防止SQL注入；将;.%等字符替换为""
                String safeField = item.getField().replaceAll("[^a-zA-Z0-9_]", "");
                if ("desc".equalsIgnoreCase(item.getOrder())) {
                    page.addOrder(OrderItem.desc(safeField));
                } else {
                    page.addOrder(OrderItem.asc(safeField));
                }
            }
        }
        return page;
    }
}