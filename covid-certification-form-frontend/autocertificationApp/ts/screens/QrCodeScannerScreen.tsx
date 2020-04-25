import { 
    View, 
    Text, 
    StyleSheet, 
    Image,
    Dimensions
} from "react-native";
import * as React from "react";
import { Content, Container } from "native-base";
import { commonStyles } from "../utils/commonStyles";
import QRCodeScanner from 'react-native-qrcode-scanner';
import { useDispatch } from "react-redux";
import { storeSearchedCertification } from "../store/actions";

type Props = {
    navigation: any,
    route: any
}

const styles = StyleSheet.create({
    container: {
        flex: 1, 
        flexDirection: 'column', 
        alignContent: 'center', 
        padding: 24,
        backgroundColor: 'white'
    }
}) 


function QrCodeScannerScreen(props: Props) {

    const dispatch = useDispatch();
    const storeSearchedCert = (cert: {id: string, data: any}) => dispatch(storeSearchedCertification(cert));

    const onSuccess = (item: any) => {
        const cert = JSON.parse(item.data)

        // salva il form
        storeSearchedCert({id: cert.id, data: cert });
        // naviga al form
        props.navigation.navigate('Form', { data: cert, isPoliceUser: true } )
    }

    return(
        <Container>

        <Content bounces={false} style={styles.container}>
            <Image style={commonStyles.repImageSmall} source={require("../img/logo_repIt_blue.png")}/>
            <Text style={commonStyles.header}>{"AUTODICHIARAZIONE AI SENSI DEGLI ARTT. 46 E 47 D.P.R. N. 445/2000"}</Text>

            <View style={{width: '100%', paddingVertical: 30}}>
            <QRCodeScanner
                    onRead={onSuccess}
                    //flashMode={QRCodeScanner.Constants.FlashMode.torch}
                    topContent={
                        <Text style={{textAlign: 'center'}}>
                            Scannerizza il QR Code dell'autodichiarazione da verificare
                        </Text>
                    }
                />
                    
                </View>
         </Content>
         </Container>
    );
}

export default QrCodeScannerScreen