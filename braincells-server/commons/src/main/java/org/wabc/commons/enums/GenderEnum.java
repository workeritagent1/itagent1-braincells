package org.wabc.commons.enums;

import lombok.Getter;
import org.wabc.commons.base.IBaseEnum;


/**
 * 性别枚举.
 *
 * @author wabc
 * @version 1.0
 * @since 2024-05-22
 */
public enum GenderEnum implements IBaseEnum<Integer> {

    MALE(1, "男"),
    FEMALE(2, "女"),
    UNKNOWN(0, "未知");

    @Getter
    // @EnumValue //  Mybatis-Plus 提供注解表示插入数据库时插入该值
    private Integer value;

    @Getter
    // @JsonValue //  表示对枚举序列化时返回此字段
    private String label;

    GenderEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }
}
