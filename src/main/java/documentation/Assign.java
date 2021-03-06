package documentation;

import java.lang.annotation.*;

@Repeatable(value = Assigns.class)
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.FIELD,ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.PACKAGE, ElementType.PARAMETER, ElementType.TYPE, ElementType.TYPE_PARAMETER, })
public @interface Assign {
    Developer developer();
    int[] issues();
}
