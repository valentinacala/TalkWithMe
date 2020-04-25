import { View, Text, Image, StyleSheet, TextInput, KeyboardAvoidingView, Platform, SafeAreaView, TouchableWithoutFeedback, Keyboard, Alert, TouchableOpacity, FlatList } from "react-native";
import * as React from "react";
import { Content, Button, Container } from "native-base";
import { commonStyles } from "../utils/commonStyles";
import { useDispatch, useSelector } from "react-redux";
import { storeSearchedCertification } from "../store/actions";
import { searchedCertificationsSelector } from "../store/reducers/reducer";
import moment from "moment";

const styles = StyleSheet.create({
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

});

type Props = {
    navigation: any
};

export default function RecoverFormScreen( props: Props) {
    const [idCertificato, setIdCertificato] = React.useState('');

    const dispatch = useDispatch();
    const storeSearchedCert = (cert: {id: string, data: any}) => dispatch(storeSearchedCertification(cert));

    const onChangeInputText = (id: any) => {setIdCertificato(id)};

    const onPress = () => {
        if(idCertificato === ''){
            Alert.alert('Non hai inserito nessun id!')
        } else {
            getCertificationData()
        }
    }

    async function getCertificationData() {
        try {
          let response = await fetch(`https://covid-certification-form.eu-gb.mybluemix.net/api/certifications/${idCertificato}`, {
            method: 'GET'
          });
  
          let json = await response.json();

          if(json.error === true){
              Alert.alert("Non ho trovato nessuna autodichiarazione per l'id indicato.")
          }  else{

          }
          
          if(json.id !== undefined) {
            storeSearchedCert({id: json.id, data: json});
            console.warn('store cortification ' + json.id)

            props.navigation.navigate('Form', { data: json, isPoliceUser: true })
          }
  
        } catch (error) {
          console.error(error);
        }
    };

    const SpacerView = (
        <View style={styles.spacer}></View>
    )
    return(
        <Container style={{backgroundColor: 'white'}}>
        <Content bounces={false} style={styles.container}>
            <View style={{justifyContent: 'center', alignContent: 'center'}}>
                {SpacerView}

                <Text style={styles.text}>{"Indica l'identificazione dell'autodichiarazione che vuoi visualizzare:"}</Text>
                {SpacerView}
                <TextInput style={[commonStyles.textInputForm, {width: '100%'}]} onChangeText={onChangeInputText} keyboardType={'numeric'}></TextInput>
                {SpacerView}
                {SpacerView}
                <Button style={commonStyles.buttonPolice} onPress={onPress}>
                    <Text style={commonStyles.buttonText}>{"Cerca"}</Text>
                </Button>
            </View>

            {SpacerView}
            
        </Content>
        {SpacerView}
        {SpacerView}
        <Image style={commonStyles.repImage} source={require("../img/logo_repIt_blue.png")}/>
        {SpacerView}
        {SpacerView}
        {SpacerView}
        </Container>
    );
}