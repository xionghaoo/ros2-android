cmake_minimum_required(VERSION 3.22.1)

project("ros2android")

set(libname ros2android)

include_directories(
        ${CMAKE_SOURCE_DIR}/include)

file(GLOB src-files
        ${CMAKE_SOURCE_DIR}/*.cpp)

add_library( # Sets the name of the library.
        ${libname}
        # Sets the library as a shared library.
        SHARED
        # Provides a relative path to your source file(s).
        ${src-files} )

find_library( # Sets the name of the path variable.
        log-lib
        # Specifies the name of the NDK library that
        # you want CMake to locate.
        log )

target_link_libraries(
        # Specifies the target library.
        ${libname}
        # opencv_imgproc opencv_core opencv_dnn opencv_dnn tegra_hal tbb cpufeatures
        # Links the target library to the log library
        # included in the NDK.
        ${log-lib}
)