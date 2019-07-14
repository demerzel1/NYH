//
//  ViewController.swift
//  MyAppVHLFC
//

import UIKit
import BMSCore


class ViewController: UIViewController , UITextFieldDelegate{
    //MARK: Properties
    @IBOutlet weak var nameTextField: UITextField!
    
    @IBOutlet weak var nameLabel: UILabel!
    
    //MARK: UITextFieldDelegate
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        nameTextField.resignFirstResponder();
        return true;
    }
    
    func textFieldDidEndEditing(_ textField: UITextField) {
        nameLabel.text = nameTextField.text;
    }
    
    @IBAction func setLabel(_ sender: UIButton) {
        nameLabel.text = "login";
    }
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.

        
        NotificationCenter.default.addObserver(self, selector: #selector(didBecomeActive), name: NSNotification.Name.UIApplicationDidBecomeActive, object: nil)
        
        nameTextField.delegate = self;
        
    }
    
    func sendRequest( urlStr: String, userType: String) {
        let session = URLSession.shared
        let url = URL(string: urlStr)!
        var request = URLRequest(url: url)
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        request.httpMethod = "POST"
        let locParam = String("123.34" + ":" + "134.34")
        let data = (locParam?.data(using: .utf8))! as Data
        request.httpBody = data
        let task = session.dataTask(with: url){ data, response, error in
            if error != nil || data == nil {
                print("Client Error!!")
                return
            }
            guard let response = response as? HTTPURLResponse, (200...299).contains(response.statusCode) else {
                print("Servier Error!!")
                return
            }
            guard let mime = response.mimeType, mime == "application/json" else {
                print("Wrong MIME type!!")
                return
            }
            do {
                let json = try JSONSerialization.jsonObject(with: data!, options: [])
                print(json)
                self.showNeedLabel.text = json as? String
            } catch{
                print("JSON error")
            }
        }
        task.resume()
    }

    @IBAction func userType(_ sender: Any) {
        
    }
    
    @IBAction func LOGIN(_ sender: Any) {
        let userType = nameTextField.text
        var urlStr = String()
        if userType == "admin" {
            urlStr = "localhost:8080/api/help"
        }else if userType == "needhelp" {
            urlStr = "localhost:8080/api/help"
        }else if userType == "volunteer" {
            urlStr = "localhost:8080/api/user"
        }else {
            print("User Type not exits!")
            self.showNeedLabel.text = "User Type not exits!"
            return
        }
        self.sendRequest(urlStr: urlStr, userType:userType!)
    }
    @IBOutlet weak var showNeedLabel: UILabel!
    func didBecomeActive(_ notification: Notification) {

    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }


}
