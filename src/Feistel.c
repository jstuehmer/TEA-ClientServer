#include "encrypt.h"
#include "decrypt.h"

JNIEXPORT void JNICALL Java_Feistel_encryptSignal
(JNIEnv *env, jobject object, jbyteArray signal, jlongArray key) {
    jboolean is_copy_signal;
    jboolean is_copy_key;
    jsize len = (*env)->GetArrayLength(env, signal);
    jbyte *arr = (*env)->GetByteArrayElements(env, signal, &is_copy_signal);
    jlong *k = (*env)->GetLongArrayElements(env, key, &is_copy_key);
    long *i = (long *)arr;
    while ((jbyte *) i < arr + len) {
        encrypt(i, (long *) k);
        i += 2;
    }
    (*env)->ReleaseByteArrayElements(env, signal, arr, is_copy_signal);
    (*env)->ReleaseLongArrayElements(env, key, k, is_copy_key);
    return;
}

JNIEXPORT void JNICALL Java_Feistel_decryptSignal
(JNIEnv *env, jobject object, jbyteArray signal, jlongArray key) {
    jboolean is_copy_signal;
    jboolean is_copy_key;
    jsize len = (*env)->GetArrayLength(env, signal);
    jbyte *arr = (*env)->GetByteArrayElements(env, signal, &is_copy_signal);
    jlong *k = (*env)->GetLongArrayElements(env, key, &is_copy_key);
    long *i = (long *) arr;
    while ((jbyte *) i < arr + len) {
        decrypt(i, (long *)k);
        i += 2;
    }
    (*env)->ReleaseByteArrayElements(env, signal, arr, is_copy_signal);
    (*env)->ReleaseLongArrayElements(env, key, k, is_copy_key);
    return;
}
