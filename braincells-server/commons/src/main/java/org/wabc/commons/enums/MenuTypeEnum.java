package org.wabc.commons.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;
import org.wabc.commons.base.IBaseEnum;


/**
 * 菜单类型枚举.
 *
 * @author wabc
 * @version 1.0
 * @since 2024-05-22
 */
public enum MenuTypeEnum implements IBaseEnum<Integer> {

    NULL(0, null),
    MENU(1, "菜单"),
    CATALOG(2, "目录"),
    EXTLINK(3, "外链");

    @Getter
    @EnumValue //  Mybatis-Plus 提供注解表示插入数据库时插入该值
    private Integer value;

    @Getter
    // @JsonValue //  表示对枚举序列化时返回此字段
    private String label;

    MenuTypeEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }

}
