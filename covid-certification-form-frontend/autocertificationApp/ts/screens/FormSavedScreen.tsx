import { View, Text, StyleSheet, KeyboardAvoidingView, Platform, SafeAreaView, TouchableWithoutFeedback, Keyboard, Clipboard, Alert, Image } from "react-native";
import * as React from "react";
import { Content, Button, Container } from "native-base";
import { commonStyles } from "../utils/commonStyles";

const styles = StyleSheet.create({
    header: {
        fontWeight: '700', 
        textAlign: 'center'
    },
    spacer: {
        width: '100%', 
        height: 16
    },
    container: {
        flex: 1, 
        flexDirection: 'column', 
        alignContent: 'flex-start', 
        padding: 20,
        backgroundColor: 'white'
    },
    paragraph: {
        flexDirection: 'row', 
        flexWrap: 'wrap'
    },
    text: {marginTop: 3},
    textInput: {
        marginTop: 3,
        backgroundColor: '#BBD4E9',
        borderBottomColor: 'black',
        borderBottomWidth: 0.33,
    },
    long: {
        width: 200
    }, 
    short: {
        width: 30
    }
});

type Props = {
    navigation: any,
    route: any
};

function FormSavedScreen( props: Props) {
    const SpacerView = (
        <View style={styles.spacer}></View>
    )
    
    const { id } = props.route.params;

    return(
        <Container style={{backgroundColor: 'white'}}>
        <KeyboardAvoidingView
            behavior={Platform.OS == "ios" ? "padding" : "height"}
            style={{flex: 1}}
        >
        <SafeAreaView style={{flex: 1}}>
        <TouchableWithoutFeedback onPress={Keyboard.dismiss}>
        <Content bounces={false} style={styles.container}>
        <View style={{justifyContent: 'flex-end'}}>
            {SpacerView}
            {SpacerView}
            {SpacerView}
            <Text style={[styles.text, {alignSelf: 'center', fontWeight: '700', fontSize: 30, color: '#59A0FF'}]}>{"Certificazione salvata!"}</Text>
            {SpacerView}
            {SpacerView}
            {SpacerView}
            <View style={{flexDirection: 'row'}}>
            <Text style={styles.text}>{"Identificativo della certificazione: "}</Text>
            <Text style={[styles.text, {fontWeight:'700'}]}>{id}</Text>
            </View>
            {SpacerView}
            <Text style={styles.text}>
                {"Copia l'identificativo per poter recuperarne le informazioni in seguito"}
            </Text>
            {SpacerView}
            {SpacerView}
            <Button  
                style={{backgroundColor: '#59A0FF', flexDirection: 'row', justifyContent: 'space-around'}} 
                onPress={() => {Clipboard.setString(id); Alert.alert('Identificativo copiato negli appunti')}}>
                <Text style={{textAlign:'center', color: '#FFFFFF'}}>{"Copia identificativo"}</Text>
            </Button>
            {SpacerView}
            <Button  
                style={{backgroundColor: '#59A0FF', flexDirection: 'row', justifyContent: 'space-around'}} 
                onPress={() => {props.navigation.navigate('Citizen')}}>
                <Text style={{textAlign:'center', color: '#FFFFFF'}}>{"Torna alla Home"}</Text>
            </Button>
               
          
            <View style={{width: '100%', height: 50}}/>
            <View style={{ flex : 1 }} />
            </View>
         </Content>
         </TouchableWithoutFeedback>
         </SafeAreaView>
        </KeyboardAvoidingView>
        <Image style={commonStyles.repImage} source={require("../img/logo_repIt_blue.png")}/>
        {SpacerView}
         {SpacerView}
         {SpacerView}
        </Container>
    );
}

export default FormSavedScreen