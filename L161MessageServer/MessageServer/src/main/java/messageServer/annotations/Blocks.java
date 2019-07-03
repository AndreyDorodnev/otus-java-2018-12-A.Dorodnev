package messageServer.annotations;

import java.lang.annotation.*;

@Documented
@Inherited
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface Blocks {
}
