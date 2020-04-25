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
import QRCode from 'react-native-qrcode-svg';

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


function QrCodeScreen(props: Props) {

    const { qrcodeData } = props.route.params;

    const windowWidth = Dimensions.get('window').width;

    return(
        <Container>

        <Content bounces={false} style={styles.container}>
            <Image style={commonStyles.repImageSmall} source={require("../img/logo_repIt_blue.png")}/>
            <Text style={commonStyles.header}>{"AUTODICHIARAZIONE AI SENSI DEGLI ARTT. 46 E 47 D.P.R. N. 445/2000"}</Text>

            <View style={{width: '100%', paddingVertical: 30}}>
                    <QRCode
                        value={qrcodeData}
                        size={windowWidth-(24*2)}
                        color={"black"}
                        backgroundColor={"white"}
                    />
                    <View style={{flex: 1}}/>
                </View>
         </Content>
         </Container>
    );
}

export default QrCodeScreen