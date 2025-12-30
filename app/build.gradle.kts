import com.android.build.gradle.ProguardFiles.getDefaultProguardFile

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("maven-publish")
    id ("signing")
}

android {
    namespace = "com.tapmind.customadapter_admob"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
        version = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    compileOnly("com.google.android.gms:play-services-ads:23.6.0")
    implementation("io.github.ravirising26:tapmindsdk:1.0.0")
}

publishing {
    publications {
        create<MavenPublication>("release") {
            afterEvaluate {
                from(components["release"])
            }

            groupId = "io.github.ravirising26"
            artifactId = "customadapter-admob"
            version = "1.0.8"

            pom {
                name.set("Custom Adapter AdMob")
                description.set("AdMob custom mediation adapter")
                url.set("https://github.com/ravirising26/customadapter-admob")

                licenses {
                    license {
                        name.set("Apache License 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0")
                    }
                }

                developers {
                    developer {
                        id.set("ravirising26")
                        name.set("Ravi Rising")
                        url.set("https://github.com/ravirising26")
                    }
                }

                scm {
                    connection.set(
                        "scm:git:https://github.com/ravirising26/customadapter-admob.git"
                    )
                    developerConnection.set(
                        "scm:git:ssh://github.com/ravirising26/customadapter-admob.git"
                    )
                    url.set("https://github.com/ravirising26/customadapter-admob")
                }
            }
        }
    }

    repositories {
        maven {
            name = "sonatype"

            url = uri(
                if (version.toString().endsWith("SNAPSHOT"))
                    "https://s01.oss.sonatype.org/content/repositories/snapshots/"
                else
                    "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
            )

            credentials {
                username = findProperty("mavenCentralUsername") as String
                password = findProperty("mavenCentralPassword") as String
            }
        }
    }
}

signing {
    useGpgCmd()
    sign(publishing.publications["release"])
}