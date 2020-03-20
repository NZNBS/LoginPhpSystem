#include <pthread.h>
#include "src/KittyMemory/MemoryPatch.h"
#include "src/Logger.h"
#include <jni.h>

struct Patch {
    MemoryPatch antenaBody;
    MemoryPatch removerParaquedas;
    MemoryPatch chuvaHack1;
    MemoryPatch chuvaHack2;
} Patches;

bool antena;
bool removerParaquedas;
bool chuvaHack;

void* hack_thread(void*) {
    ProcMap il2cppMap;
    do {
        il2cppMap = KittyMemory::getLibraryMap("libil2cpp.so");
        sleep(1);
    } while(!il2cppMap.isValid());

	Patches.antenaBody = MemoryPatch("libunity.so", 0x2A3454 , "\x10\x40\x1C\x46", 4);
    Patches.removerParaquedas = MemoryPatch("libil2cpp.so", 0xAD28F0, "\x00\x00\xA0\xE3\x1E\xFF\x2F\xE1", 8);
    Patches.chuvaHack1 = MemoryPatch("libil2cpp.so", 0x1BC5AE4, "\x02\x00\xA0\xE3\x1E\xFF\x2F\xE1", 8);
    Patches.chuvaHack2 = MemoryPatch("libil2cpp.so", 0x1BC5AE4, "\x04\x00\xA0\xE3\x1E\xFF\x2F\xE1", 8);
    return NULL;
}

extern "C"
JNIEXPORT jobjectArray JNICALL Java_com_mreoz_FloatingActivityService_getListFT(JNIEnv *env, jobject jobj){
    jobjectArray ret;
    int i;
    int Total_Feature = 3;
    const char *features[]= {"TG_EXAMPLE1",
                             "TG_EXAMPLE2",
                             "SB_EXAMPLE3_0_2"};

    ret= (jobjectArray)env->NewObjectArray(Total_Feature,
                                           env->FindClass("java/lang/String"),
                                           env->NewStringUTF(""));
    for(i=0;i<Total_Feature;i++) {
        env->SetObjectArrayElement(
                ret,i,env->NewStringUTF(features[i]));
    }
    return(ret);
}

extern "C"
JNIEXPORT void JNICALL Java_com_mreoz_FloatingActivityService_changeToggle(JNIEnv *env, jobject thisObj, jint number) {
int i = (int) number;
switch (i) {
    case 0:
        antena = !antena;
        if (antena) {
            Patches.antenaBody.Modify();
        } else {
            Patches.antenaBody.Restore();
        }

        break;

    case 1:
        removerParaquedas = !removerParaquedas;
        if (removerParaquedas) {
            Patches.removerParaquedas.Modify();
        } else {
            Patches.removerParaquedas.Restore();
        }
        break;
		}
    return;
}
extern "C"
JNIEXPORT void JNICALL Java_com_mreoz_FloatingActivityService_changeSeekBar(JNIEnv *env, jobject thisObj, jint id, jint value) {
   if (id == 3) {
           chuvaHack = !chuvaHack;
           if (value == 1) {
               Patches.chuvaHack1.Modify();
           } else if (value == 2) {
               Patches.chuvaHack2.Modify();
           } else {
               Patches.chuvaHack1.Restore();
           }
       }
}
__attribute__((constructor))
void libloaded() {
    pthread_t ptid;
    pthread_create(&ptid, nullptr, hack_thread, nullptr);
}
