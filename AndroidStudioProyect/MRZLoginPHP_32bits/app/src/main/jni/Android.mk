LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := MRZ

LOCAL_SRC_FILES := Main.cpp \ src/KittyMemory/KittyMemory.cpp \ src/KittyMemory/MemoryPatch.cpp \
				   
include $(BUILD_SHARED_LIBRARY)