package org.wabc.commons.model;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 分页响应结果
 *  属性可读性强，隐藏后端分页框架
 *
 * @author wabc
 * @version 1.0
 * @since 2025-05-10
 */
@Data
@Schema(description = "分页响应结果")
public class PageResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ArraySchema(
            arraySchema = @Schema(description = "当前页数据列表", requiredMode = Schema.RequiredMode.REQUIRED),
            schema = @Schema(implementation = Object.class)
    )
    private List<T> items = Collections.emptyList();

    @Schema(description = "当前页码（从1开始）", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private long pageNum;

    @Schema(description = "每页数量", example = "10", requiredMode = Schema.RequiredMode.REQUIRED, minimum = "1")
    private long pageSize;

    @Schema(description = "总记录数", example = "100", requiredMode = Schema.RequiredMode.REQUIRED, minimum = "0")
    private long total;

    @Schema(description = "总页数", example = "10", requiredMode = Schema.RequiredMode.REQUIRED, minimum = "0")
    private long totalPages;

    // ================= 构造方法 =================
    public PageResult() {}

    public PageResult(List<T> items, long pageNum, long pageSize, long total) {
        this.items = items != null ? items : Collections.emptyList();
        this.pageNum = pageNum;
        this.pageSize = validatePageSize(pageSize);
        this.total = total;
        this.totalPages = calculateTotalPages();
    }

    public static <T> PageResult<T> from(IPage<T> page) {
        return new PageResult<>(
                page.getRecords(),
                page.getCurrent(),
                page.getSize(),
                page.getTotal()
        );
    }

    // ================= 核心逻辑 =================
    private long validatePageSize(long pageSize) {
        if (pageSize <= 0) {
            throw new IllegalArgumentException("pageSize 必须大于0");
        }
        return pageSize;
    }

    private long calculateTotalPages() {
        return (total + pageSize - 1) / pageSize;
    }
}