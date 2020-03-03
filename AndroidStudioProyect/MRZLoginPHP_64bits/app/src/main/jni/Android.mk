LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := MRZ

LOCAL_SRC_FILES := Main.cpp \ src/KittyMemory/KittyMemory.cpp \ src/KittyMemory/MemoryPatch.cpp \ Unity/Vector2.hpp \ Unity/Vector3.hpp \ Unity/Quaternion.hpp \ Substrate/hde64.c \ Substrate/SubstrateDebug.cpp \ Substrate/SubstrateHook.cpp \ Substrate/SubstratePosixMemory.cpp \
				   
include $(BUILD_SHARED_LIBRARY)