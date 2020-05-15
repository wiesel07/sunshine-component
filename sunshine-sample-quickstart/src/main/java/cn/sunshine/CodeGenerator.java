package com.healthy.hello.mbg;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author wuj
 * @since 2020年3月15日
 */
public class CodeGenerator {

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
       
       // idea的时候需要配置 String tempPath="/hello-mbg";
        String tempPath="";
        gc.setOutputDir(projectPath +tempPath+"/src/main/java");
        gc.setAuthor("wuj");
        gc.setOpen(false);
        gc.setSwagger2(true); // 实体属性 Swagger2 注解
        
        gc.setFileOverride(true);
        gc.setBaseResultMap(true);
        gc.setDateType(DateType.ONLY_DATE);
        gc.setBaseColumnList(true);
        gc.setIdType(IdType.ASSIGN_ID);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://47.112.194.132:3306/hello?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai");
        // dsc.setSchemaName("public");
        //dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("healthyMysqlAdmin211");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(scanner("模块名"));
        pc.setParent("com.healthy.hello");
        pc.setEntity("model");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        //	String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath +tempPath+ "/src/main/resources/mapper/" + pc.getModuleName() + "/"
                        + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        /*
         * cfg.setFileCreate(new IFileCreate() {
         *
         * @Override public boolean isCreate(ConfigBuilder configBuilder, FileType
         * fileType, String filePath) { // 判断自定义文件夹是否需要创建 checkDir("调用默认方法创建的目录");
         * return false; } });
         */
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        // 指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        // templateConfig.setController();

        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        // strategy.setSuperEntityClass("你自己的父类实体,没有就不用设置!");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setEntityColumnConstant(true);
        strategy.setEntityBuilderModel(true);
        strategy.setSuperMapperClass("com.healthy.hello.mbg.common.config.MyBaseMapper");
        // 表填充字段
        List<TableFill> tableFillList = new ArrayList<>();
        TableFill tableFill = new TableFill("update_time", FieldFill.INSERT_UPDATE);
        tableFillList.add(tableFill);
        tableFill = new TableFill("create_time", FieldFill.INSERT);
        tableFillList.add(tableFill);
        strategy.setTableFillList(tableFillList);
        strategy.setLogicDeleteFieldName("del_flag");
        // 公共父类
        // strategy.setSuperControllerClass("你自己的父类控制器,没有就不用设置!");
        // 写于父类中的公共字段
        //	strategy.setSuperEntityColumns("id");

        strategy.setInclude("bus_checkout",
        		"bus_order",
        		"bus_payment",
        		"bus_user_collection",
        		"res_class",
        		"res_class_attach",
        		"res_class_dispatch",
        		"res_class_label",
        		"res_class_label_ref",
        		"res_coach",
        		"res_coach_level",
        		"res_coupon",
        		"res_coupon_type",
        		"res_coupon_use",
        		"res_region",
        		"res_room",
        		"res_room_level",
        		"res_user",
        		"res_user_level",
        		"sys_dict",
        		"sys_dict_item");
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
        //	mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }
}
