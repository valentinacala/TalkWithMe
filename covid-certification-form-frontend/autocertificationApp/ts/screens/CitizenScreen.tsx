import { View, Image, StyleSheet } from "react-native";
import { Button, Text, Content, Container } from 'native-base'
import * as React from "react";
import { commonStyles } from "../utils/commonStyles";

type Props = {
    navigation: any
};

const mockedData = {
    "id": "1d2y3lk8foq919",
    "anagrafica": 
        {"cognome": "Gialli", 
        "comuneDiDomicilio": "VT", 
        "comuneDiNascita": "VT", 
        "comuneDiResidenza": "RM", 
        "dataDiNascita": "24/05/2000", 
        "indirizzoDiDomicilio": "Via Como 10", 
        "indirizzoDiResidenza": "Via Como 10", 
        "luogoDiNascita": "Roma", 
        "nome": "Valentina"
    }, 
    "dichiarazioni": {
        "dichiarazione1": false, 
        "dichiarazione2": false, 
        "dichiarazione3": true, 
        "dichiarazione4": false
    }, 
    "documento": {
        "dataDiRilascioDelDocuemento": "10/10/2019", 
        "enteCheHaErogatoIlDocumento": "Motorizzazione", 
        "numeroDocumento": "123bsdf6", 
        "tipologiaDiDocumento": "Patente",
        "utenzaTelefonica": "333 9988882"
    }, 
    "sign_date": "2020-03-31T09:14:28.797Z", 
    "spostamento": {
        "RegioneDiDestinazione": "Lazio", 
        "RegioneDiPartenza": "Lazio", 
        "dettagliSulloSpostamento": "Portare la spesa ad una coppia di anziani", 
        "indirizzoDiDestinazione": "Via libertà 20", 
        "indirizzoDiPartenza": "Via Como 10", 
        "motivoDelloSpostamento": "Supporto soggetti fragili",
        "provvedimentoRegionale": "prova"
    }
};

const styles = StyleSheet.create({
    spacer: {width: '100%', height: 16},
    content: {flex: 1 , flexDirection: 'column', alignContent: 'center',  paddingHorizontal: 16},
})

function CitizenScreen(props: Props) {
    const SpacerView = (
        <View style={styles.spacer}></View>
    )

    return(
        <Container style={{backgroundColor: 'white'}}>

        <Content style={styles.content}>
                {SpacerView}
                <Button  
                    style={commonStyles.button} 
                    onPress={() =>{
                        props.navigation.navigate('RecoverOld')
                }}>
                    <Text style={commonStyles.buttonText}>{"Visualizza le tue autodichiarazioni"}</Text></Button>
                {SpacerView}
                <Button style={commonStyles.button} onPress={() =>{props.navigation.navigate('GuidedForm')}}>
                    <Text style={commonStyles.buttonText}>{"Crea una nuova autodichiarazione"}</Text>
                </Button>
                
         </Content>
         <Image style={commonStyles.repImage} source={require("../img/logo_repIt_blue.png")}/>
         {SpacerView}
         {SpacerView}
         {SpacerView}
         </Container>

    );
}

export default  CitizenScreen;