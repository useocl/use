/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2004 Mark Richters, University of Bremen
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.  
 */

/* $ProjectHeader: use 0.393 Wed, 16 May 2007 14:10:28 +0200 opti $ */

#include <jni.h>
#include "org_tzi_use_util_input_GNUReadline.h"
#include <stdio.h>
#include <stdlib.h>
#include <readline/readline.h>
#include <readline/history.h>

/*
 * Class:     org_tzi_use_util_input_GNUReadline
 * Method:    prepareClose
 * Signature: ()V
 */
JNIEXPORT void JNICALL 
Java_org_tzi_use_util_input_GNUReadline_close(JNIEnv *env, 
						 jobject obj)
{

  rl_done = 1;  
  rl_free_line_state();
  rl_deprep_terminal();
}

/*
 * Class:     org_tzi_use_util_input_GNUReadline
 * Method:    readline
 * Signature: (Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL 
Java_org_tzi_use_util_input_GNUReadline_readline(JNIEnv *env, 
						 jobject obj, 
						 jstring prompt)
{
    jstring result;
    char *line;
    const char *cprompt = (*env)->GetStringUTFChars(env, prompt, 0);

    /* Get a line from the user. */
    line = readline((char *) cprompt);

    /* If the line has any text in it, save it on the history. */
    if ( line && *line)
	add_history(line);

    /* Create the Java result string object. */
    result = (*env)->NewStringUTF(env, line);

    /* Free memory. */
    (*env)->ReleaseStringUTFChars(env, prompt, cprompt);
    free(line);

    return result;
}

/*
 * Class:     org_tzi_use_util_input_GNUReadline
 * Method:    usingHistory
 * Signature: ()V
 */
JNIEXPORT void JNICALL 
Java_org_tzi_use_util_input_GNUReadline_usingHistory(JNIEnv *env, 
						     jobject obj)
{
    using_history();
}

/*
 * Class:     org_tzi_use_util_input_GNUReadline
 * Method:    readHistory
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL 
Java_org_tzi_use_util_input_GNUReadline_readHistory(JNIEnv *env,
						    jobject obj, 
						    jstring filename)
{
    const char *cfilename = (*env)->GetStringUTFChars(env, filename, 0);
    int res = read_history((char *) cfilename);
    if ( res ) {
	/* Could not read file, res == errno */
	char *msg = strerror(res);
	jclass io_exception = (*env)->FindClass(env, "java/io/IOException");
	(*env)->ThrowNew(env, io_exception, msg);
    }
    return;
}

/*
 * Class:     org_tzi_use_util_input_GNUReadline
 * Method:    writeHistory
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL 
Java_org_tzi_use_util_input_GNUReadline_writeHistory(JNIEnv *env, 
						     jobject obj, 
						     jstring filename) 
{
    const char *cfilename = (*env)->GetStringUTFChars(env, filename, 0);
    int res = write_history((char *) cfilename);
    if ( res ) {
	/* Could not write file, res == errno */
	char *msg = strerror(res);
	jclass io_exception = (*env)->FindClass(env, "java/io/IOException");
	(*env)->ThrowNew(env, io_exception, msg);
    }
    return;
}					
