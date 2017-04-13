#include <jni.h>
#include <stdio.h>
#include <stdlib.h>
#include "Feistel.h"

void encrypt(int *v, int *k) {

    /* TEA encryption algorithm */
    unsigned int y = v[0], z = v[1], sum = 0;
    unsigned int delta = 0x9e3779b9, n = 32;

    while(n-- > 0) {
        sum += delta;
        y += (z<<4) + k[0] ^ z + sum ^ (z>>5) + k[1];
        z += (y<<4) + k[2] ^ y + sum ^ (y>>5) + k[3];
    }

    v[0] = y;
    v[1] = z;
}

void decrypt(int *v, int *k) {

    /* TEA decryption routine */
    unsigned int n = 32, sum, y = v[0], z = v[1];
    unsigned int delta = 0x9e3779b9l;

    sum = delta << 5;
    while (n-- > 0) {
        z -= (y<<4) + k[2] ^ y + sum ^ (y>>5) + k[3];
        y -= (z<<4) + k[0] ^ z + sum ^ (z>>5) + k[1];
        sum -= delta;
    }

    v[0] = y;
    v[1] = z;
}

JNIEXPORT void JNICALL Java_Feistel_encryptSignal
(JNIEnv *env, jclass c, jbyteArray signal, jintArray key) {
    jboolean is_copy_signal;
    jboolean is_copy_key;
    jsize len = (*env)->GetArrayLength(env, signal);
    jbyte *arr = (*env)->GetByteArrayElements(env, signal, &is_copy_signal);
    jint *k = (*env)->GetIntArrayElements(env, key, &is_copy_key);
    int *i = (int *)arr;
    while ((jbyte *) i < arr + len) {
        encrypt(i, (int *) k);
        i += 2;
    }
    (*env)->ReleaseByteArrayElements(env, signal, arr, is_copy_signal);
    (*env)->ReleaseIntArrayElements(env, key, k, is_copy_key);
    return;
}

JNIEXPORT void JNICALL Java_Feistel_decryptSignal
(JNIEnv *env, jclass c, jbyteArray signal, jintArray key) {
    jboolean is_copy_signal;
    jboolean is_copy_key;
    jsize len = (*env)->GetArrayLength(env, signal);
    jbyte *arr = (*env)->GetByteArrayElements(env, signal, &is_copy_signal);
    jint *k = (*env)->GetIntArrayElements(env, key, &is_copy_key);
    int *i = (int *) arr;
    while ((jbyte *) i < arr + len) {
        decrypt(i, (int *)k);
        i += 2;
    }
    (*env)->ReleaseByteArrayElements(env, signal, arr, is_copy_signal);
    (*env)->ReleaseIntArrayElements(env, key, k, is_copy_key);
    return;
}
