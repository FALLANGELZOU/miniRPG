import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.22"
    id("io.papermc.paperweight.userdev") version "1.5.11"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.angel"
version = "0.1"

repositories {
//    mavenCentral()
    maven {
        setUrl("https://maven.aliyun.com/repository/public")
        name = "aliyun"
    }

    maven {
        name = "papermc-repo"
        setUrl("https://repo.papermc.io/repository/maven-public/")
    }
    maven {
        name = "sonatype"
        setUrl("https://oss.sonatype.org/content/groups/public/")
    }
    maven {
        name = "aikar"
        setUrl("https://repo.aikar.co/content/groups/aikar/" )
    }
}

dependencies {
    implementation("com.mybatis-flex:mybatis-flex-kotlin-extensions:1.0.5")
    implementation("com.mybatis-flex:mybatis-flex-core:1.7.9")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.2")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.15.2")
    annotationProcessor("com.mybatis-flex:mybatis-flex-processor:1.7.9")
    implementation("com.alibaba:druid:1.2.21")
    implementation("com.zaxxer:HikariCP:5.1.0")
    implementation("co.aikar:acf-paper:0.5.1-SNAPSHOT")
    // Other Dependencies
    paperweight.paperDevBundle("1.20.4-R0.1-SNAPSHOT")
}


tasks {
    assemble {
        dependsOn(reobfJar) //  重新混淆
    }
    build {
        dependsOn(shadowJar)    //  使用shadowJar
    }
    withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.compilerArgs.addAll(arrayOf("-parameters"))
//        options.isFork = true
//        options.forkOptions.executable = "javac"
    }

    withType<KotlinCompile> {

        kotlinOptions {
            jvmTarget = "17"

            freeCompilerArgs = listOf("-Xjvm-default=all")
            kotlinOptions.javaParameters = true //  cant understand
        }
    }

    withType<Jar> {
        //  将kotlin运行环境打包进jar
        //  https://stackoverflow.com/questions/44197521/gradle-project-java-lang-noclassdeffounderror-kotlin-jvm-internal-intrinsics

        // Otherwise you'll get a "No main manifest attribute" error
        manifest {
            attributes["Main-Class"] = "com.angel.mc.MiniRPG"
        }

        // To avoid the duplicate handling strategy error
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE

        // To add all of the dependencies
        from(sourceSets.main.get().output)

        dependsOn(configurations.runtimeClasspath)
        from({
            configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
        })
    }

    shadowJar {

        //  使用shadowJar进行重定向
        relocate("co.aikar.commands", "com.angel.mc.lib.acf")
        relocate("co.aikar.locales", "com.angel.mc.lib.locales")
    }
}


// 编译配置
java {
    withSourcesJar()
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17

}



kotlin {

}


tasks.register<Copy>("copy2plugin") {
    group = "build"

    dependsOn("build")
    doFirst {

    }
    from(layout.buildDirectory.dir("libs/miniRPG-0.1.jar"))
    into("$projectDir/paper/plugins")

}






//configurations.all {
//    resolutionStrategy {
//        eachDependency {
//            if (requested.group == "org.ow2.asm") {
//                useVersion("9.5")
//            }
//        }
//    }
//}
